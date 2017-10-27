package topologies;

import java.bolts.EmailCounter;
import java.bolts.EmailExtractor;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import spouts.CommitFeedListener;

public class EmailCounterTopology {
    public static StormTopology build() {
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        topologyBuilder
                .setSpout("commit-feed-listener", new CommitFeedListener());

        topologyBuilder
                .setBolt("email-extractor", new EmailExtractor())
                .shuffleGrouping("commit-feed-listener");

        topologyBuilder
                .setBolt("email-counter", new EmailCounter())
                .fieldsGrouping("email-extractor", new Fields("email"));

        return topologyBuilder.createTopology();
    }
}
