package socialite.yarn;

import java.io.File;
import java.io.IOException;

import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.StringInterner;
import org.apache.hadoop.yarn.api.ApplicationConstants;
import org.apache.hadoop.yarn.api.ApplicationConstants.Environment;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.ApplicationSubmissionContext;
import org.apache.hadoop.yarn.api.records.ContainerLaunchContext;
import org.apache.hadoop.yarn.api.records.LocalResource;
import org.apache.hadoop.yarn.api.records.LocalResourceType;
import org.apache.hadoop.yarn.api.records.LocalResourceVisibility;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.client.api.YarnClientApplication;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.hadoop.yarn.util.ConverterUtils;
import org.apache.hadoop.yarn.util.Records;

public class Client {
    public static final String yarnAppType = "SocialiteMaster";

    Configuration yarnConf = new YarnConfiguration();

    public Path getHdfsJarPath(String jarFile) throws IOException {
        FileSystem fs = FileSystem.get(new Configuration());
        return new Path(fs.getHomeDirectory(), "socialite" + Path.SEPARATOR + jarFile);
    }

    YarnClient createYarnClient() {
        YarnConfiguration conf = new YarnConfiguration();
        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(conf);
        yarnClient.start();
        return yarnClient;
    }

    List<ApplicationReport> getRunningAppMaster(YarnClient yarnClient) throws IOException, YarnException {
        HashSet<String> appTypes = new HashSet(); appTypes.add(yarnAppType);
        EnumSet<YarnApplicationState> appStats = EnumSet.of(YarnApplicationState.SUBMITTED,
                                                            YarnApplicationState.ACCEPTED,
                                                            YarnApplicationState.RUNNING);

        return yarnClient.getApplications(appTypes, appStats);
    }

    boolean isAppMasterRunning(YarnClient yarnClient) throws IOException, YarnException {
        return getRunningAppMaster(yarnClient).size() > 0;
    }
    public boolean killAppMaster() throws Exception {
        YarnClient yarnClient = createYarnClient();
        List<ApplicationReport> appReports = getRunningAppMaster(yarnClient);
        if (appReports.size() == 0) {
            System.out.println("Socialite AppMaster is not running!");
            return false;
        }

        if (appReports.size() > 1) {
            System.out.println("[WARN] Multiple Socialite AppMaster is not running! # "+appReports.size());
        }

        ApplicationId appMasterId = appReports.get(0).getApplicationId();
        yarnClient.killApplication(appMasterId);
        return true;
    }

    public void startAppMaster(final Path localJarPath) throws Exception {
        YarnClient yarnClient = createYarnClient();
        if (isAppMasterRunning(yarnClient)) {
            System.out.println("Socialite AppMaster is already up and running!");
            return;
        }

        YarnClientApplication app = yarnClient.createApplication();

        LocalResource appMasterJar = Records.newRecord(LocalResource.class);
        setupAppMasterJar(localJarPath, appMasterJar);

        ContainerLaunchContext masterContainer = Records.newRecord(ContainerLaunchContext.class);
        Map<String, String> env = System.getenv();
        masterContainer.setCommands(
                Collections.singletonList(
                        "$JAVA_HOME/bin/java -ea " +
                                env.get("JVM_OPTS") + " " +
                                env.get("SOCIALITE_OPTS") + " " +
                                "socialite.yarn.SocialiteAppMaster " +
                                ConverterUtils.getPathFromYarnURL(appMasterJar.getResource()) +
                                " 1>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stdout" +
                                " 2>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stderr"
                )
        );
        masterContainer.setLocalResources(Collections.singletonMap("socialite.jar", appMasterJar));

        Map<String, String> appMasterEnv = new HashMap<String, String>(env);
        setupAppMasterEnv(appMasterEnv);
        masterContainer.setEnvironment(appMasterEnv);

        Resource capability = Records.newRecord(Resource.class);
        capability.setMemory(2048);
        capability.setVirtualCores(4);

        ApplicationSubmissionContext appContext = app.getApplicationSubmissionContext();
        appContext.setApplicationType("SocialiteMaster");
        appContext.setApplicationName("Socialite AppMaster"); // application name
        appContext.setAMContainerSpec(masterContainer);
        appContext.setResource(capability);
        appContext.setQueue("default"); // queue

        ApplicationId appId = appContext.getApplicationId();
        yarnClient.submitApplication(appContext);

        ApplicationReport appReport;
        YarnApplicationState appState;
        while (true) {
            appReport = yarnClient.getApplicationReport(appId);
            appState = appReport.getYarnApplicationState();
            if (appState == YarnApplicationState.RUNNING) {
                System.out.println("Successfully launched Socialite AppMaster!");
                System.out.println("See its status at "+appReport.getTrackingUrl());
                break;
            } else if (appState == YarnApplicationState.SUBMITTED ||
                       appState == YarnApplicationState.ACCEPTED) {
                Thread.sleep(1000);
            } else {
                throw new RuntimeException("Failed to launch Socialite AppMaster. YarnApplicationState:"+appState);
            }
        }
    }

    void setupAppMasterJar(Path localJarPath, LocalResource appMasterJar) {
        try {
            FileSystem fs = FileSystem.get(new Configuration());
            Path jarPath = getHdfsJarPath(String.format("socialite-all-%d.jar", System.currentTimeMillis()/1000));
            fs.copyFromLocalFile(localJarPath, jarPath);

            FileStatus jarStat = FileSystem.get(yarnConf).getFileStatus(jarPath);
            appMasterJar.setResource(ConverterUtils.getYarnUrlFromPath(jarPath));
            appMasterJar.setSize(jarStat.getLen());
            appMasterJar.setTimestamp(jarStat.getModificationTime());
            appMasterJar.setType(LocalResourceType.FILE);
            appMasterJar.setVisibility(LocalResourceVisibility.PUBLIC);
        } catch (IOException e) {
            throw new RuntimeException("Cannot copy jar file to HDFS:"+ e.getMessage());
        }
    }

    void addToEnv(Map<String, String> env, String variable, String value) {
        String classPathSep = ApplicationConstants.CLASS_PATH_SEPARATOR;
        String val = env.get(variable);
        if (val == null) { val = value; }
        else { val = val + classPathSep + value; }

        env.put(StringInterner.weakIntern(variable), StringInterner.weakIntern(val));
    }
    void setupAppMasterEnv(Map<String, String> appMasterEnv) {
        for (String c : yarnConf.getStrings(
                YarnConfiguration.YARN_APPLICATION_CLASSPATH,
                YarnConfiguration.DEFAULT_YARN_APPLICATION_CLASSPATH)) {
            addToEnv(appMasterEnv, Environment.CLASSPATH.name(), c.trim());
        }
        addToEnv(appMasterEnv, Environment.CLASSPATH.name(), Environment.PWD.$() + File.separator + "*");
    }


    public static void main(String[] args) throws Exception {
        Client c = new Client();
        String cmd = args[0].toLowerCase();
        if (cmd.equals("start")) {
            Path jarPath = new Path(args[1]);
            c.startAppMaster(jarPath);
        } else if (cmd.equals("stop")) {
            c.killAppMaster();
        } else {
            throw new AssertionError("Unexpected command:"+cmd);
        }
    }
}
