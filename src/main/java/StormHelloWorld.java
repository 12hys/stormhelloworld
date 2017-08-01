import bolts.EmailCounter;
import bolts.EmailExtractor;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;
import spouts.CommitFeedListener;

public class StormHelloWorld {
    private static final int TEN_MINUTES = 600000;

    public static void main(String[] args) {
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        topologyBuilder
                .setSpout("commit-feed-listener", new CommitFeedListener());

        topologyBuilder
                .setBolt("email-extractor", new EmailExtractor())
                .shuffleGrouping("commit-feed-listener");

        topologyBuilder
                .setBolt("email-counter", new EmailCounter())
                .fieldsGrouping("email-extractor", new Fields("email"));

        Config config = new Config();
        config.setDebug(true);

        StormTopology topology = topologyBuilder.createTopology();
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("github-topology", config, topology);

        Utils.sleep(TEN_MINUTES);
        localCluster.killTopology("github-topology");
        localCluster.shutdown();
    }
}