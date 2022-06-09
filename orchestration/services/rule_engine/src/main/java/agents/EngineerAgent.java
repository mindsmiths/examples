package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import external.websockets.WebSocketAPI;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;

@Data
public class EngineerAgent extends Agent {
    public static String ID = "ENGINEER";

    public EngineerAgent() {
        id = ID;
    }

    public EngineerAgent(String connectionName, String connectionId) {
        super(connectionName, connectionId);
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
        WebSocketAPI.send(robotId, "RESTART");;
    }
}

