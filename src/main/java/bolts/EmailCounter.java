package bolts;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class EmailCounter extends BaseBasicBolt {
    private Map<String, Integer> counts;

    public void prepare(Map stormConf, TopologyContext context) {
        counts = new HashMap<String, Integer>();
    }

    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String email = tuple.getStringByField("email");
        counts.put(email, countFor(email) + 1);
        printCounts();
    }

    private Integer countFor(String email) {
        Integer count = counts.get(email);
        return count == null ? 0 : count;
    }

    private void printCounts() {
        for (String email : counts.keySet()) {
            System.out.println(String.format("%s has count of %s", email, counts.get(email)));
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        // Don't declare anything
    }
}
