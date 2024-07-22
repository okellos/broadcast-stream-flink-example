package bcast;

import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;

public class DataStreamJob {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Define rules stream
        DataStream<Rule> rulesStream = env.fromElements(
                new Rule("rule1", "condition1"),
                new Rule("rule2", "condition2")
        );

        // Create a state descriptor for broadcast state
        MapStateDescriptor<String, Rule> ruleStateDescriptor
                = new MapStateDescriptor<>(
                        "RulesBroadcastState",
                        BasicTypeInfo.STRING_TYPE_INFO,
                        TypeInformation.of(new TypeHint<Rule>() {
                        })
                );

        // Broadcast the rules stream and register the state descriptor
        BroadcastStream<Rule> broadcastRuleStream = rulesStream
                .broadcast(ruleStateDescriptor);

        // Define event logs stream
        DataStream<EventLogs> eventLogStream = env.fromElements(
                new EventLogs("condition1"),
                new EventLogs("condition5"),
                new EventLogs("condition1"),
                new EventLogs("condition7")
        );
        
        // Connect event logs stream with broadcasted rules stream
        DataStream<String> processedStream = eventLogStream
                .connect(broadcastRuleStream)
                .process(new RuleBroadcastProcessFunction(ruleStateDescriptor));

        processedStream.print();

        // Execute program
        env.execute("Broadcast Rule Stream");
    }
}
