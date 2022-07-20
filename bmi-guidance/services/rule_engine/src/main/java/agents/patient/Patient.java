package agents.patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import com.mindsmiths.gpt3.GPT3AdapterAPI;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;
import com.mindsmiths.sdk.utils.Utils;
import com.mindsmiths.telegramAdapter.MediaData;
import com.mindsmiths.telegramAdapter.MediaType;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends Agent {
    private Integer age;
    private Integer height;
    private Integer lowWeightRange;
    private Integer highWeightRange;
    private boolean waitingForAnswer;
    private Date lastInteractionTime;
    private boolean pinged;


    public Patient(String connectionName, String connectionId,
                   int age, int height, Pair<Integer, Integer> normalWeightRange) {
        super(connectionName, connectionId);
        this.age = age;
        this.height = height;
        this.lowWeightRange = normalWeightRange.getLeft();
        this.highWeightRange = normalWeightRange.getRight();
    }

    public void sendMessage(String text) {
        String chatId = connections.get("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text);
    }

    public void sendWelcomeMessage() {
        String chatId = connections.get("telegram");
        List<MediaData> mediaList = new ArrayList<>();
        Character character = Utils.randomChoice(Character.characters);
        mediaList.add(new MediaData(MediaType.photo, character.getImageLink(),
            String.format(character.getText(), age, height, lowWeightRange, highWeightRange)));
        TelegramAdapterAPI.sendMessage(chatId, mediaList);
    }

    public void askGPT3(String prompt) {
        Log.info("Prompt for GPT-3:\n" + prompt);
        GPT3AdapterAPI.complete(
            prompt, // input prompt
            "text-davinci-001", // model
            128, // max tokens
            0.31 // temperature
        );
    }


    // ************************* Texts *************************

    public static List<String> confirmationTexts = List.of(
        "Thanks, I'll check with the doctor!",
        "Thanks, let me get back to you.",
        "Thank you, the doctor will reply shortly.",
        "Thanks, I'll get back to you with the response!"
    );
}
