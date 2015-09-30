package socialite.yarn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.registry.client.api.RegistryOperations;
import org.apache.hadoop.registry.client.api.RegistryOperationsFactory;
import org.apache.hadoop.registry.client.types.Endpoint;
import org.apache.hadoop.registry.client.types.ServiceRecord;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import static org.apache.hadoop.registry.client.api.RegistryConstants.REGISTRY_PREFIX;

public class SocialiteAppMasterClient {
    public static final Log L= LogFactory.getLog(SocialiteAppMasterClient.class);

    static SocialiteAppMasterClient inst;
    static {
        try { inst = new SocialiteAppMasterClient(); }
        catch (IOException e) { L.error("Cannot create SocialiteAppMasterClient:"+e); }
    }

    public static SocialiteAppMasterClient get() { return inst; }

    ServiceRecord record;
    SocialiteAppMasterClient() throws IOException {
        RegistryOperations regOps = RegistryOperationsFactory.createInstance(new YarnConfiguration());
        regOps.start();

        String path = REGISTRY_PREFIX + "socialite.master";
        record = regOps.resolve(path);
    }

    public String getHost() {
        Endpoint endpoint = record.getExternalEndpoint("query");
        Map<String, String> addr = endpoint.addresses.get(0);
        if (addr == null) { L.error("Cannot find master-node hostname"); }
        return addr.get("host");
    }

    public int getPort(String proto) {
        Endpoint endpoint = record.getExternalEndpoint(proto);
        Map<String, String> addr = endpoint.addresses.get(0);
        if (addr == null) { L.error("Cannot find port for "+proto); }
        return Integer.parseInt(addr.get("port"));
    }
}
