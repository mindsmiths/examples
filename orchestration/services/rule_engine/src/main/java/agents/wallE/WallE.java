package agents.wallE;

import com.mindsmiths.ruleEngine.model.Agent;

import external.websockets.WebSocketAPI;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
public class WallE extends Agent {
    public static final String ID = "WallE";

    private Date lastCleaned;
    private boolean attemptingRestart;

    public WallE(String connectionName, String connectionId) {
        super(connectionName, connectionId);
    }

    public void attemptRestart() {
        WebSocketAPI.send(ID, "RESTART");;
    }
}
