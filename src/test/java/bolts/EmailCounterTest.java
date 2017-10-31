package bolts;

import org.apache.storm.Config;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.tuple.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmailCounterTest {
    private EmailCounter emailCounterBolt;
    private OutputCollector outputCollector;
    private TopologyContext topologyContext;

    @Before
    public void before() {
        emailCounterBolt = new EmailCounter();
        outputCollector = mock(OutputCollector.class);
        topologyContext = mock(TopologyContext.class);

        emailCounterBolt.prepare(new Config(), topologyContext, outputCollector);
    }

    @Test
    public void executeBolt() {
        Tuple inputTuple = mock(Tuple.class);
        when(inputTuple.getStringByField("email")).thenReturn("test@example.com");

        Map<String, Integer> counts = new HashMap<String, Integer>();
        counts.put("test@example.com", 1);

        emailCounterBolt.execute(inputTuple);

        Map<String, Integer> actualCounts = emailCounterBolt.getCounts();
        assertEquals(1, actualCounts.size());
        assertEquals(1, actualCounts.get("test@example.com").intValue());
        verify(outputCollector).ack(eq(inputTuple));

    }
}