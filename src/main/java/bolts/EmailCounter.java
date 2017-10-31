package bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class EmailCounter extends BaseRichBolt {
    private Map<String, Integer> counts;
    private OutputCollector outputCollector;

    public void prepare(Map stormConf, TopologyContext context, OutputCollector outputCollector) {
        counts = new HashMap<String, Integer>();
        this.outputCollector = outputCollector;
    }

    public void execute(Tuple tuple) {
        String email = tuple.getStringByField("email");
        counts.put(email, countFor(email) + 1);
        outputCollector.ack(tuple);
        outputCollector.emit(new Values(counts));
    }

    private Integer countFor(String email) {
        Integer count = counts.get(email);
        return count == null ? 0 : count;
    }

    public Map<String, Integer> getCounts() {
        return counts;
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }
}
