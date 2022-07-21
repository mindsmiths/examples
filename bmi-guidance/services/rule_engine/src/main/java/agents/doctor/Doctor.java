package agents.doctor;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.sdk.utils.Utils;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import lombok.Data;
import lombok.NoArgsConstructor;
import signals.BMIMeasurement;
import signals.Prediction;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
public class Doctor extends Agent {
    public static String ID = "DOCTOR";

    private Integer modelVersion;
    private String currentRequest;
    private int handledAutomatically;
    private int pendingRequests;
    private Date lastNotifiedForPending = new Date();


    public Doctor(String connectionName, String connectionId) {
        super(connectionName, connectionId);
        this.id = Doctor.ID;
    }

    public void sendMessage(String text) {
        TelegramAdapterAPI.sendMessage(connections.get("telegram"), text);
    }

    public void sendBMIMeasurement(Prediction prediction) {
        BMIMeasurement bmi = prediction.getBmiMeasurement();
        System.out.printf("Confidence is %f", prediction.getConfidence());

        String confidence = Utils.randomChoice(requestLowConfidenceTexts);
        if (prediction.getConfidence() > 0.6) {
            confidence = String.format(Utils.randomChoice(requestConfidenceTexts), prediction.getConfidence() * 100);
        }

        String predictionResult = prediction.getPrediction() ? "obese" : "not obese";

        TelegramAdapterAPI.sendMessage(
            connections.get("telegram"),
            Utils.randomChoice(requestQuestionTexts) +
                String.format("Age: %d\nBMI: %.1f\n", bmi.getAge(), bmi.getBMI()) +
                String.format(Utils.randomChoice(requestPredictionTexts), predictionResult) +
                confidence,
            new KeyboardData(
                prediction.getPredictionId(),
                Arrays.asList(
                    new KeyboardOption("YES", "Obese"),
                    new KeyboardOption("NO", "Not obese")
                )
            )
        );
    }


    // ************************* Texts *************************

    public static List<String> confirmationTexts = List.of(
        "You rock, doc!",
        "Thanks!",
        "Much obliged!",
        "Thanks for the input!",
        "Much appreciated!"
    );

    public static List<String> welcomeTexts = List.of(
        "Hello, doc!",
        "Nice to have you here, doc!",
        "Welcome, doc!",
        "Good to see you, doc!"
    );

    public static List<String> requestQuestionTexts = List.of(
        "Can you help me with this case?\n",
        "Would you say this child is obese?\n",
        "What about this case?\n",
        "Can I get your opinion on this case?\n",
        "What would you say for this child?\n"
    );

    public static List<String> requestPredictionTexts = List.of(
        "\nI think the child is %s",
        "\nI would say it is %s",
        "\nI say %s",
        "\nMy estimate is %s",
        "\nI estimate %s"
    );

    public static List<String> requestLowConfidenceTexts = List.of(
        ", but I'm not very sure.",
        ", but I'm not too certain.",
        ", but I'm not overly confident.",
        ", but I would appreciate some help."
    );

    public static List<String> requestConfidenceTexts = List.of(
        ". I'm %.0f%% sure.",
        ". I'm %.0f%% certain.",
        ". I'm %.0f%% confident."
    );
}
