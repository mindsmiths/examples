package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.websocketadapter.WebSocketAPI;
import lombok.Data;
import signals.RestartRobot;

import java.time.LocalDateTime;
import java.util.Arrays;

@Data
public class Engineer extends Agent {
    public static String ID = "ENGINEER";

    public Engineer() {
        id = ID;
    }

    public Engineer(String telegramId) {
        super("telegram", telegramId);
        id = ID;
    }

    public void sendMessage(String text) {
        TelegramAdapterAPI.sendMessage(connections.get("telegram"), text);
    }

    public void sendCrashOptions(String text, String robotId) {
        KeyboardData keyboardData = new KeyboardData(
            robotId,
            Arrays.asList(
                new KeyboardOption("AUTO", "Try restarting robot"),
                new KeyboardOption("MANUAL", "I'll take it from here"),
                new KeyboardOption("SUPER", "Notify superior")
            )
        );
        keyboardData.setExpireAt(LocalDateTime.now().plusSeconds(60));
        TelegramAdapterAPI.sendMessage(
            connections.get("telegram"),
            text,
            keyboardData
        );
    }

    public void attemptRestart(String robotId) {
        WebSocketAPI.send(robotId, new RestartRobot());
    }
}

