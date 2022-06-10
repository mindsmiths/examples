package agents;

import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;
import external.websockets.WebSocketAPI;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;

import java.util.List;


@Data
@NoArgsConstructor
public class SuperiorAgent extends Agent {
    public static String ID = "SUPERIOR";

    public SuperiorAgent(EngineerAgent agent) {
        setConnections(agent.getConnections());
        id = ID;
    }

    public void sendMessage(String text) {
        TelegramAdapterAPI.sendMessage(connections.get("telegram"), text);
    }

    public void sendCrashOptions(String text, String robotId) {
        KeyboardData keyboardData = new KeyboardData(
                robotId,
                List.of(new KeyboardOption("AUTO", "Restart"))
        );
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
