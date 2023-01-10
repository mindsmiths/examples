package agents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.api.KeyboardData;
import com.mindsmiths.telegramAdapter.api.KeyboardOption;
import com.mindsmiths.telegramAdapter.api.MediaData;


@Getter
@Setter
@NoArgsConstructor
public class PlayerAgent extends Agent {
    private String name;

    public void sendWelcomeMessage(String name) {
        sendMessage("Welcome, " + name + "!\n" +
                        "The goal of the game is to guess the number of followers a person or brand has on Twitter. " +
                        "You have 3 guesses per game.\nReady? Press start to play! 😊",
            new KeyboardData("start-game", List.of(new KeyboardOption("NEW_GAME", "Start game")))
        );
    }


    // *** Telegram communication ***

    public void sendMessage(String text) {
        String chatId = getConnection("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text);
    }

    public void sendMessage(String text, KeyboardData keyboardData) {
        String chatId = getConnection("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text, keyboardData);
    }

    public void sendMessage(List<MediaData> media) {
        String chatId = getConnection("telegram");
        TelegramAdapterAPI.sendMessage(chatId, media);
    }
}
