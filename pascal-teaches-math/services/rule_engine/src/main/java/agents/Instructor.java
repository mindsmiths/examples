package agents;

import agents.models.Neuron;
import agents.models.Task;
import com.google.common.primitives.Ints;
import com.mindsmiths.armory.ArmoryAPI;
import com.mindsmiths.armory.components.*;
import com.mindsmiths.armory.templates.BaseTemplate;
import com.mindsmiths.armory.templates.TemplateGenerator;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.sdk.conf.BaseSettings;
import com.mindsmiths.sdk.utils.Utils;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.ProbabilityUtils;

import java.util.*;

@Data
@NoArgsConstructor
public class Instructor extends Agent {

    // Here we track how well our user has learned to multiply with a certain digit
    Map<String, Neuron> digitLevelMap = new HashMap<>();  // maps must have String keys because of serialization
    public static final double NEURON_CAPACITY = 100;
    public static final double NEURON_RESISTANCE = 0.05;

    Task currentTask;
    Date taskSentAt;


    public Instructor(String connectionName, String connectionId) {
        super(connectionName, connectionId);
        initializeNeurons();
    }


    // ********** Neurons **********

    private void initializeNeurons() {
        if (digitLevelMap.isEmpty()) {
            for (int i = 1; i <= 10; i++)
                digitLevelMap.put(String.valueOf(i), new Neuron(NEURON_RESISTANCE, NEURON_CAPACITY));
        }
    }

    public Neuron getDigitNeuron(int digit) {
        return digitLevelMap.get(String.valueOf(digit));
    }

    public void decayNeurons() {
        for (int i = 1; i <= 10; i++)
            getDigitNeuron(i).decay();
    }


    // ********** Task generation **********

    public Task generateTask() {
        // TODO: Three types of tasks

        List<Double> digitSuggestionValues = digitLevelMap.values().stream().map((n) -> 1 - n.getValue()).toList();
        List<Double> pdf = ProbabilityUtils.generateProbabilityDensityFunction(digitSuggestionValues);

        // Generate digits
        int leftDigit = ProbabilityUtils.sample(pdf) + 1;
        int rightDigit = ProbabilityUtils.sample(pdf) + 1;

        List<Integer> otherSolutions = generateOtherSolutions(leftDigit, rightDigit);

        return new Task(leftDigit, rightDigit, otherSolutions);
    }

    public List<Integer> generateOtherSolutions(Integer leftDigit, Integer rightDigit) {
        List<Integer> otherSolutions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int otherLeft = generateSimilarDigit(leftDigit);
            int otherRight = generateSimilarDigit(rightDigit);

            // If we have already generated this answer, generate again
            while (otherLeft * otherRight == leftDigit * rightDigit || otherSolutions.contains(otherLeft * otherRight))
                otherRight = generateSimilarDigit(rightDigit);

            otherSolutions.add(otherLeft * otherRight);
        }

        return otherSolutions;
    }

    private int generateSimilarDigit(int digit) {
        return Ints.constrainToRange(digit + new Random().nextInt(5) - 2, 1, 10);
    }


    // ********** Armory **********

    public void showQuestionScreen() {
        // Generate answer buttons
        List<BaseSubmitButtonComponent> options = new ArrayList<>(4);
        String correctAnswer = String.valueOf(currentTask.correctAnswer());
        options.add(new PrimarySubmitButtonComponent("result", correctAnswer, correctAnswer));
        for (Integer ans : currentTask.getOtherAnswers())
            options.add(new PrimarySubmitButtonComponent("result", String.valueOf(ans), String.valueOf(ans)));

        Collections.shuffle(options);

        // Show screen
        BaseTemplate screen = new TemplateGenerator()
            .addComponent("title", new TitleComponent("Multiplication table!"))
            .addComponent("description",
                new DescriptionComponent(
                    "How much is " + currentTask.getLeft() + " x " + currentTask.getRight() + "?"))
            .addComponent("result", new ActionGroupComponent(options));

        showScreen(screen);
        taskSentAt = new Date();
    }

    public void answeredTask(boolean correctly) {
        double secondsPassed = (new Date().getTime() - taskSentAt.getTime()) / 1000.;

        if (correctly) {
            // charge with decaying function w.r.t. time
            // the longer it took you to answer, the less you've actually learned it
            double value = Math.min(1 / secondsPassed, 3);
            getDigitNeuron(currentTask.getLeft()).charge(value);
            getDigitNeuron(currentTask.getRight()).charge(value);
        } else {
            // discharge with slowly growing function w.r.t. time
            // the longer it took you to answer, the more we're sure you've learned it incorrectly
            double value = Math.log(secondsPassed + 1) * 2;
            getDigitNeuron(currentTask.getLeft()).discharge(value);
            getDigitNeuron(currentTask.getRight()).discharge(value);
        }
    }

    public void showIntroScreen() {
        BaseTemplate screen = new TemplateGenerator()
            .addComponent("title", new TitleComponent("Hello there! \uD83D\uDC4B"))
            .addComponent("description", new DescriptionComponent("Little practice and you'll be a pro in multiplying numbers from 1 to 10 in no time. \uD83D\uDE09"))
            .addComponent("start", new PrimarySubmitButtonComponent("answer", "Start", "yes"));
        showScreen(screen);
    }

    public void showResultScreen(boolean correctly) {
        String message = correctly ? Utils.randomChoice(correctTextList) : Utils.randomChoice(incorrectTextList);
        String neuronState = "";
        if (BaseSettings.DEBUG)
            for (int i = 1; i <= 10; i++)
                neuronState += "Neuron " + i + ": " + getDigitNeuron(i).getValue() + "<br/>";

        BaseTemplate screen = new TemplateGenerator()
            .addComponent("title", new TitleComponent(message))
            .addComponent("askAgain", new TitleComponent("Do you want to play again?"))
            .addComponent("answer", new ActionGroupComponent(List.of(
                new PrimarySubmitButtonComponent("answer", "Sure!", "yes"),
                new PrimarySubmitButtonComponent("answer", "No, thanks", "no"))))
            .addComponent("neurons", new DescriptionComponent(neuronState));
        showScreen(screen);
    }

    public void showGoodbyeScreen() {
        BaseTemplate screen = new TemplateGenerator()
            .addComponent("title", new TitleComponent("See you some other time!"));
        showScreen(screen);
    }

    public void showScreen(BaseTemplate screen) {
        ArmoryAPI.showScreen(getConnection("armory"), screen);
    }

    public void showScreens(String firstScreenId, Map<String, BaseTemplate> screens) {
        ArmoryAPI.showScreens(getConnection("armory"), firstScreenId, screens);
    }


    // ********** Texts **********

    List<String> correctTextList = List.of(
        "That's right! \uD83D\uDE09",
        "You know it \uD83D\uDE01",
        "Correct! \uD83D\uDE0A",
        "Well done \uD83E\uDD29",
        "Nice! \uD83E\uDD17",
        "Indeed! \uD83E\uDD2D");

    List<String> incorrectTextList = List.of("Not quite! \uD83D\uDE14",
        "That was close!",
        "Incorrect \uD83E\uDDD0",
        "That's not it \uD83D\uDE14",
        "Almost!  \uD83D\uDE25",
        "Hmm, maybe next time... \uD83E\uDD14");

}
