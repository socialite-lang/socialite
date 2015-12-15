package socialite.yarn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.net.NetUtils;
import org.apache.hadoop.registry.client.api.BindFlags;
import org.apache.hadoop.registry.client.api.RegistryOperations;
import org.apache.hadoop.registry.client.api.RegistryOperationsFactory;
import org.apache.hadoop.registry.client.types.ServiceRecord;
import org.apache.hadoop.util.StringInterner;
import org.apache.hadoop.yarn.api.ApplicationConstants;
import org.apache.hadoop.yarn.api.ApplicationConstants.Environment;
import org.apache.hadoop.yarn.api.records.*;
import org.apache.hadoop.yarn.client.api.AMRMClient.ContainerRequest;
import org.apache.hadoop.yarn.client.api.NMClient;
import org.apache.hadoop.yarn.client.api.async.AMRMClientAsync;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.hadoop.yarn.util.ConverterUtils;
import org.apache.hadoop.yarn.util.Records;
import org.apache.hadoop.registry.client.binding.RegistryTypeUtils;
import socialite.dist.PortMap;
import socialite.dist.master.MasterNode;

import static org.apache.hadoop.registry.client.api.RegistryConstants.*;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * This class implements Socialite app master.
 * In real usages, the callbacks should execute in a separate thread or thread pool
 */
public class SocialiteAppMaster implements AMRMClientAsync.CallbackHandler {
    public static final Log L= LogFactory.getLog(SocialiteAppMaster.class);
    Configuration configuration;
    NMClient nmClient;
    AMRMClientAsync<ContainerRequest> rmClient;
    int numAllocedContainers;
    final Path jarPath;
    volatile boolean alive = true;
    volatile FinalApplicationStatus finalStatus = FinalApplicationStatus.UNDEFINED;
    String appMessage = "";

    public SocialiteAppMaster(Path _jarPath) {

        jarPath = _jarPath;
        configuration = new YarnConfiguration();
        numAllocedContainers = 0;

        nmClient = NMClient.createNMClient();
        nmClient.init(configuration);
        nmClient.start();

        rmClient = AMRMClientAsync.createAMRMClientAsync(100, this);
        rmClient.init(getConfiguration());
        rmClient.start();
    }

    void setupWorkerJar(LocalResource workerJar) throws IOException {
        FileStatus jarStat = FileSystem.get(configuration).getFileStatus(jarPath);
        workerJar.setResource(ConverterUtils.getYarnUrlFromPath(jarPath));
        workerJar.setSize(jarStat.getLen());
        workerJar.setTimestamp(jarStat.getModificationTime());
        workerJar.setType(LocalResourceType.FILE);
        workerJar.setVisibility(LocalResourceVisibility.PUBLIC);
    }
    void addToEnv(Map<String, String> env, String variable, String value) {
        String classPathSep = ApplicationConstants.CLASS_PATH_SEPARATOR;
        String val = env.get(variable);
        if (val == null) { val = value; }
        else { val = val + classPathSep + value; }

        env.put(StringInterner.weakIntern(variable), StringInterner.weakIntern(val));
    }
    void setupWorkerEnv(Map<String, String> workerEnv) {
        for (String c : configuration.getStrings(
                YarnConfiguration.YARN_APPLICATION_CLASSPATH,
                YarnConfiguration.DEFAULT_YARN_APPLICATION_CLASSPATH)) {
            addToEnv(workerEnv, Environment.CLASSPATH.name(), c.trim());
        }
        addToEnv(workerEnv, Environment.CLASSPATH.name(), Environment.PWD.$() + File.separator + "*");
    }
    void launchWorkerNode(Container container) throws IOException, YarnException {
        Map<String, String> env = System.getenv();
        ContainerLaunchContext workerContext = Records.newRecord(ContainerLaunchContext.class);

        LocalResource workerJar = Records.newRecord(LocalResource.class);
        setupWorkerJar(workerJar);
        workerContext.setLocalResources(Collections.singletonMap("socialite.jar", workerJar));
        System.out.println("[Master] workerJar:"+workerJar);

        Map<String, String> workerEnv = new HashMap<String, String>(env);
        setupWorkerEnv(workerEnv);
        workerContext.setEnvironment(workerEnv);

        String opts = "-Dsocialite.master=" + NetUtils.getHostname().split("/")[1] + " ";
        opts += "-Dsocialite.worker.num_threads=" + ClusterConf.get().getNumWorkerThreads() + " ";
        workerContext.setCommands(
                Collections.singletonList(
                        "$JAVA_HOME/bin/java -ea " +
                                env.get("JVM_OPTS") + " " +
                                env.get("SOCIALITE_OPTS") + " " + opts + " " +
                                "socialite.dist.worker.WorkerNode" +
                                " 1>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stdout" +
                                " 2>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stderr"
                ));
        nmClient.startContainer(container, workerContext);
        L.info("Launched worker node (container:"+container.getId()+")");
    }
    public void onContainersAllocated(List<Container> containers) {
        L.info("onContainersAllocated: # containers="+containers.size());
        for (Container container:containers) {
            try { launchWorkerNode(container); }
            catch (Exception e) {
                L.error("Error launching worker-node: container-id="+container.getId()+" "+e);
                die();
            }
        }
    }

    public void onContainersCompleted(List<ContainerStatus> statuses) {
        for (ContainerStatus status : statuses) {
            synchronized (this) {
                numAllocedContainers++;
                if (allContainersAllocated()) {
                    L.info("Successfully allocated "+numAllocedContainers+" worker nodes.");
                }
                finalStatus = FinalApplicationStatus.SUCCEEDED;
            }
        }
    }

    void die() {
        finalStatus = FinalApplicationStatus.FAILED;
        alive = false;
    }

    public void onNodesUpdated(List<NodeReport> updated) {
        L.warn("onNodesUpdated:" + updated);
    }

    public void onReboot() {
        L.warn("onReboot");
    }

    public void onShutdownRequest() {
        L.warn("onShutdownRequest");
        alive = false;
    }

    public void onError(Throwable t) {
        appMessage = "Cannot allocate workers:" + t.getMessage();
        L.fatal(appMessage);
        die();
    }

    public float getProgress() {
        return 0;
    }

    public boolean allContainersAllocated() {
        return numAllocedContainers == ClusterConf.get().getNumWorkers();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public static void main(String[] args) throws Exception {
        SocialiteAppMaster appmaster = new SocialiteAppMaster(new Path(args[0]));
        appmaster.runMainLoop();
    }

    void startMasterNode() {
        MasterNode.startMasterNode();
    }

    public void runMainLoop() throws Exception {

        rmClient.registerApplicationMaster("", 0, "");

        initRegistry();

        startMasterNode();

        Priority priority = Records.newRecord(Priority.class);
        priority.setPriority(0);

        Resource capability = Records.newRecord(Resource.class);
        capability.setMemory(ClusterConf.get().getWorkerHeapSize());
        capability.setVirtualCores(ClusterConf.get().getNumWorkerThreads());

        List<ContainerRequest> containerReq = new ArrayList<ContainerRequest>();
        for (int i = 0; i < ClusterConf.get().getNumWorkers(); ++i) {
            ContainerRequest containerAsk = new ContainerRequest(capability, null, null, priority);
            rmClient.addContainerRequest(containerAsk);
            containerReq.add(containerAsk);
        }

        while (alive) {
            Thread.sleep(1000);
        }
        finish();

        /*
        for (ContainerRequest req : containerReq) {
            rmClient.removeContainerRequest(req);
        }
        int containersToAdd = 2;
        numContainersToWaitFor = containersToAdd;

        System.out.println("[Am] finished all containers. Asking for more containers, total=" + numContainersToWaitFor);
        for (int i = 0; i < containersToAdd; ++i) {
            ContainerRequest containerAsk = new ContainerRequest(capability, null, null, priority);
            System.out.println("[AM] Making res-req " + i);
            rmClient.addContainerRequest(containerAsk);
        }

        System.out.println("[AM] waiting for containers to finish once more!!!");
        while (!doneWithContainers()) {
            Thread.sleep(100);
        }
        */

    }
    void finish() throws IOException, YarnException {
        rmClient.unregisterApplicationMaster(finalStatus, appMessage, null);
        nmClient.stop();
    }


    void addIpcEndpoint(ServiceRecord record, String ident, int port) {
        InetSocketAddress addr = new InetSocketAddress(NetUtils.getHostname().split("/")[1], port);
        record.addExternalEndpoint(RegistryTypeUtils.ipcEndpoint(ident, addr));
    }
    void initRegistry() throws IOException {
        RegistryOperations regOps = RegistryOperationsFactory.createInstance(new YarnConfiguration());

        regOps.start();
        String path = REGISTRY_PREFIX + "socialite.master";
        ServiceRecord record;
        regOps.mknode(path, true);

        /*if (regOps.exists(path)) { record = regOps.resolve(path); } */

        record = new ServiceRecord();
        PortMap portmap = PortMap.master();
        addIpcEndpoint(record, "query", portmap.getPort("query"));
        addIpcEndpoint(record, "workerReq", portmap.getPort("workerReq"));
        addIpcEndpoint(record, "tupleReq", portmap.getPort("tupleReq"));

        regOps.bind(path, record, BindFlags.CREATE | BindFlags.OVERWRITE);
    }
}
