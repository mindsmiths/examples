import agents.Engineer;
import agents.Smith;
import agents.eve.Eve;
import agents.wallE.WallE;
import com.mindsmiths.ruleEngine.runner.RuleEngineService;
import com.mindsmiths.ruleEngine.util.Agents;
import com.mindsmiths.ruleEngine.util.Signals;
import com.mindsmiths.telegramAdapter.TelegramKeyboardAnswered;
import com.mindsmiths.telegramAdapter.TelegramReceivedMessage;


public class Runner extends RuleEngineService {
    @Override
    public void initialize() {
        configureSignals(
            Signals.on(TelegramReceivedMessage.class).sendTo((msg) -> Agents.getOrCreateByConnection("telegram", msg.getChatId(), new Engineer(msg.getChatId()))),
            Signals.on(TelegramKeyboardAnswered.class).sendTo((msg) -> Agents.getOrCreateByConnection("telegram", msg.getChatId(), new Engineer(msg.getChatId())))
        );

        if (!Agents.exists(WallE.ID))
            Agents.createAgent(new WallE());
        if (!Agents.exists(Eve.ID))
            Agents.createAgent(new Eve());
        if (!Agents.exists(Smith.ID))
            Agents.createAgent(new Smith());
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.start();
    }
}
