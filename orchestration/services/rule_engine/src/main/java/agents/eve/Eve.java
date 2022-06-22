package agents.eve;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.websocketadapter.WebSocketAPI;
import lombok.Data;
import signals.RestartRobot;

import java.util.Date;


@Data
public class Eve extends Agent {
    public static final String ID = "Eve";

    private Date lastStarted;
    private boolean attemptingRestart;

    public Eve() {
        this.id = ID;
    }

    public void attemptRestart() {
        WebSocketAPI.send(ID, new RestartRobot());
    }
}
