import com.mindsmiths.ruleEngine.runner.RuleEngineService;
import com.mindsmiths.ruleEngine.subscriptions.Events;
import com.mindsmiths.ruleEngine.util.Agents;

import agents.GameAdmin;
import models.Celebrity;


public class Runner extends RuleEngineService {
    @Override
    public void initialize() {
        configureSignals(getClass().getResourceAsStream("config/signals.yaml"));
        configureSignals(
            Events.on(Celebrity.class).sendTo("GAME_ADMIN")
        );

        if(!Agents.exists("GAME_ADMIN"))
            Agents.createAgent(new GameAdmin());
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.start();
    }
}