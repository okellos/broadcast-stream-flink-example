package bcast;

import java.util.Map;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ReadOnlyBroadcastState;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.util.Collector;

public class RuleBroadcastProcessFunction extends BroadcastProcessFunction<EventLogs, Rule, String> {

    private final MapStateDescriptor<String, Rule> ruleStateDescriptor;

    public RuleBroadcastProcessFunction(MapStateDescriptor<String, Rule> ruleStateDescriptor) {
        this.ruleStateDescriptor = ruleStateDescriptor;
    }

    @Override
    public void processElement(EventLogs event, ReadOnlyContext ctx, Collector<String> out) throws Exception {
        
        // Access the broadcast state for rules (read-only)
        final ReadOnlyBroadcastState<String, Rule> readOnlyBroadcastState = ctx.getBroadcastState(ruleStateDescriptor);

        System.out.println("Process Element: " + event);


        // Iterate over the broadcast state and apply rules
        for (Map.Entry<String, Rule> entry : readOnlyBroadcastState.immutableEntries()) {
            Rule rule = entry.getValue();

            System.out.println("Rule Broadcast: " + rule.getCondition());

            // Apply the rule to the event
            if (event.getName().contains(rule.getCondition())) {
                out.collect("We Matched event: " + event.getName() + " with Rule: " + rule.getRuleName());
            }
        }
    }

    @Override
    public void processBroadcastElement(Rule rule, Context ctx, Collector<String> out) throws Exception {

      // Update the broadcast state with the new rule
        ctx.getBroadcastState(ruleStateDescriptor).put(rule.getRuleName(), rule);

    }
}
