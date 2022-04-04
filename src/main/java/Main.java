import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.utils.Utils;
import topologies.EmailCounterTopology;

import java.time.Duration;

public class Main {
    private static final Duration sleepTime = Duration.ofMinutes(10);

    public static void main(String[] args) {

        // test
        Config config = new Config();
        config.setDebug(true);

        try {
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("github-topology", config, EmailCounterTopology.build());

            Utils.sleep(sleepTime.toMillis());
            localCluster.killTopology("github-topology");
            localCluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}