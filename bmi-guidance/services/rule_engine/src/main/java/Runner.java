import java.util.List;
import java.util.Random;
import java.util.function.Function;
import org.apache.commons.lang3.tuple.Pair;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.runner.RuleEngineService;
import com.mindsmiths.ruleEngine.util.Agents;
import com.mindsmiths.ruleEngine.util.Signals;
import com.mindsmiths.telegramAdapter.TelegramKeyboardAnswered;
import com.mindsmiths.telegramAdapter.TelegramReceivedMessage;

import agents.doctor.Doctor;
import agents.patient.Patient;
import agents.roboPatient.RoboPatient;
import agents.smith.Smith;
import utils.BMIUtils;


public class Runner extends RuleEngineService {

    @Override
    public void initialize() {
        Function<String, Object> getAgent = chatId -> {
            List<Agent> agents = Agents.getByConnection("telegram", chatId);
            if (!agents.isEmpty())
                return agents;

            // Create doctor agent
            if (!Agents.exists(Doctor.ID) && !chatId.startsWith("ROBO-"))
                return Agents.createAgent(new Doctor("telegram", chatId)).getId();

            // Create patient agent
            Random random = new Random();
            int age = random.nextInt(2, 16);
            int height = 88 + (age) * 6 + random.nextInt(-10, 11);
            Pair<Integer, Integer> normalWeightRange = Pair.of(
                BMIUtils.getWeightForHeightAndBMI(height, BMIUtils.normalBMIRangeByAge.get(age).getLeft()),
                BMIUtils.getWeightForHeightAndBMI(height, BMIUtils.normalBMIRangeByAge.get(age).getRight())
            );

            if (Agents.exists(Doctor.ID) && !chatId.startsWith("ROBO-"))
                return Agents.createAgent(new Patient(
                        "telegram", chatId, age, height, normalWeightRange)
                ).getId();

            return Agents.createAgent(new RoboPatient(
                    "telegram", chatId, age, height, normalWeightRange)
            ).getId();
        };

        configureSignals(
            Signals.on(TelegramReceivedMessage.class).sendTo((msg) -> getAgent.apply(msg.getChatId())),
            Signals.on(TelegramKeyboardAnswered.class).sendTo((ans) -> getAgent.apply(ans.getChatId()))
        );

        // Create agent Smith
        if (!Agents.exists(Smith.ID))
            Agents.createAgent(new Smith());
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.start();
    }
}
