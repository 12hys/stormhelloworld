import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.utils.Utils;
import topologies.EmailCounterTopology;

public class Main {
    private static final int TEN_MINUTES = 600000;

    public static void main(String[] args) {

        Config config = new Config();
        config.setDebug(true);

        try {
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("github-topology", config, EmailCounterTopology.build());

            Utils.sleep(TEN_MINUTES);
            localCluster.killTopology("github-topology");
            localCluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}