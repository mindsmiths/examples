import agents.EngineerAgent;
import agents.Smith;
import agents.eve.Eve;
import agents.eve.signals.EveFinishedTask;
import agents.eve.signals.EveStartedTask;
import agents.wallE.WallE;
import agents.wallE.signals.WallECleaned;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.runner.RuleEngineService;
import com.mindsmiths.ruleEngine.util.Agents;
import com.mindsmiths.ruleEngine.util.Signals;
import com.mindsmiths.telegramAdapter.TelegramKeyboardAnswered;
import com.mindsmiths.telegramAdapter.TelegramReceivedMessage;
import external.websockets.NewSocketConnection;
import external.websockets.Restarted;

import java.util.Map;


public class Runner extends RuleEngineService {
    @Override
    public void initialize() {
        Map<String, Class<? extends Agent>> robotIdToClass = Map.of(
                WallE.ID, WallE.class,
                Eve.ID, Eve.class
        );

        configureSignals(
                Signals.on(NewSocketConnection.class).sendTo((conn) -> Agents.getOrCreateByConnection("robot", conn.getConnectionId(), robotIdToClass.get(conn.getConnectionId()))),
                Signals.on(Restarted.class).sendTo((msg) -> Agents.getByConnection("robot", msg.getConnectionId())),

                Signals.on(WallECleaned.class).sendTo((msg) -> Agents.getOrCreateByConnection("robot", WallE.ID, WallE.class)),
                Signals.on(EveStartedTask.class).sendTo((msg) -> Agents.getOrCreateByConnection("robot", Eve.ID, Eve.class)),
                Signals.on(EveFinishedTask.class).sendTo((msg) -> Agents.getOrCreateByConnection("robot", Eve.ID, Eve.class)),

                Signals.on(TelegramReceivedMessage.class).sendTo((msg) -> Agents.getOrCreateByConnection("telegram", msg.getChatId(), EngineerAgent.class)),
                Signals.on(TelegramKeyboardAnswered.class).sendTo((msg) -> Agents.getOrCreateByConnection("telegram", msg.getChatId(), EngineerAgent.class))
        );

        if (!Agents.exists(Smith.ID))
            Agents.createAgent(new Smith());
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.start();
    }
}
