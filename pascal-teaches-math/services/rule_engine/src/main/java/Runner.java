import com.mindsmiths.ruleEngine.runner.RuleEngineService;


public class Runner extends RuleEngineService {
    @Override
    public void initialize() {
        configureSignals(
            getClass().getResourceAsStream("config/signals.yaml")
        );
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.start();
    }
}
