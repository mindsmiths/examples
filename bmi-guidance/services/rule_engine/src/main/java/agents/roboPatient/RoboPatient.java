package agents.roboPatient;

import agents.patient.Scenario;
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

    public RoboPatient(String connectionName, String connectionId, Scenario scenario) {
        super(connectionName, connectionId);
        this.age = scenario.getAge();
        this.height = scenario.getHeight();
        this.lowWeightRange = scenario.getNormalWeightRange().getLeft();
        this.highWeightRange = scenario.getNormalWeightRange().getRight();
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
