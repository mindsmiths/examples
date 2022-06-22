package agents.wallE;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.websocketadapter.WebSocketAPI;
import lombok.Data;
import signals.RestartRobot;

import java.util.Date;


@Data
public class WallE extends Agent {
    public static final String ID = "WallE";

    private Date lastCleaned;
    private boolean attemptingRestart;

    public WallE() {
        this.id = ID;
    }

    public void attemptRestart() {
        WebSocketAPI.send(ID, new RestartRobot());
    }
}
