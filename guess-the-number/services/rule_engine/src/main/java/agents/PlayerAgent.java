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
import com.mindsmiths.telegramAdapter.api.MediaType;

import models.Celebrity;


@Getter
@Setter
@NoArgsConstructor
public class PlayerAgent extends Agent {
    private String name;
    private boolean waitingForAnswer;

    private Celebrity currentCelebrity;
    private int numberAttempts;

    public void sendWelcomeMessage(String name) {
        sendMessage("Welcome, " + name + "! " +
                        "The goal of the game is to guess the number of followers a person or a brand has on Twitter. " +
                        "You have 3 guesses for each of them. Ready? Press start to play!"
        );
    }

    public void sendStartMessage() {
        sendMessage(List.of(new MediaData(
            MediaType.photo,
            currentCelebrity.getImageUrl(),
            "Can you guess how many followers " + currentCelebrity.getName() + " has on Twitter?")));
    }

    public void offerNewGame(String text) {
        sendMessage(text,
            new KeyboardData("offer-rematch",
                List.of(
                    new KeyboardOption("NEW_GAME", "Hit me again!"),
                    new KeyboardOption("STOP_PLAYING", "Enough for today")
                )
            )
        );
    }

    public void resetGame() {
        this.currentCelebrity = null;
        this.numberAttempts = 0;
        this.waitingForAnswer = false;
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