package agents.roboPatient;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import com.mindsmiths.roboPatient.RoboPatientAPI;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;


@Data
@NoArgsConstructor
public class RoboPatient extends Agent {
    private boolean robotInitialized;
    private Integer age;
    private Integer height;
    private Integer lowWeightRange;
    private Integer highWeightRange;

    public RoboPatient(String connectionName, String connectionId,
                   int age, int height, Pair<Integer, Integer> normalWeightRange) {
        super(connectionName, connectionId);
        this.age = age;
        this.height = height;
        this.lowWeightRange = normalWeightRange.getLeft();
        this.highWeightRange = normalWeightRange.getRight();
    }

    public void sendMessage(String text) {
        String chatId = connections.get("telegram");
        if (!robotInitialized) {
            RoboPatientAPI.init(chatId, age, height, lowWeightRange, highWeightRange);
            robotInitialized = true;
        } else
            Log.info("Received response for robot " + chatId + ": " + text);
    }
}
