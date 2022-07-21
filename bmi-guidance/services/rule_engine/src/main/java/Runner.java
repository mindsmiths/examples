import agents.doctor.Doctor;
import agents.patient.Patient;
import agents.roboPatient.RoboPatient;
import agents.smith.Smith;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.runner.RuleEngineService;
import com.mindsmiths.ruleEngine.util.Agents;
import com.mindsmiths.ruleEngine.util.Signals;
import com.mindsmiths.telegramAdapter.TelegramKeyboardAnswered;
import com.mindsmiths.telegramAdapter.TelegramReceivedMessage;
import utils.PatientUtils;

import java.util.List;


public class Runner extends RuleEngineService {

    @Override
    public void initialize() {
        configureSignals(
            Signals.on(TelegramReceivedMessage.class).sendTo((msg) -> getAgent(msg.getChatId())),
            Signals.on(TelegramKeyboardAnswered.class).sendTo((ans) -> getAgent(ans.getChatId()))
        );

        // Create agent Smith
        if (!Agents.exists(Smith.ID))
            Agents.createAgent(new Smith());
    }

    Object getAgent(String chatId) {
        // Check if agent for this chatId already exists
        List<Agent> agents = Agents.getByConnection("telegram", chatId);
        if (!agents.isEmpty())
            return agents;

        // Create robo-patient agent
        if (PatientUtils.isRobot(chatId))
            return Agents.createAgent(
                new RoboPatient("telegram", chatId, PatientUtils.generateRandomScenario())
            ).getId();

            // Create doctor agent
        else if (!Agents.exists(Doctor.ID))
            return Agents.createAgent(new Doctor("telegram", chatId)).getId();

            // Create patient agent
        else
            return Agents.createAgent(
                new Patient("telegram", chatId, PatientUtils.generateRandomScenario())
            ).getId();
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.start();
    }
}
