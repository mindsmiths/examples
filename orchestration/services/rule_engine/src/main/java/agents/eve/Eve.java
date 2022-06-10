package agents.eve;

import com.mindsmiths.ruleEngine.model.Agent;

import external.websockets.WebSocketAPI;

import lombok.*;

import java.util.Date;


@Data
@NoArgsConstructor
public class Eve extends Agent {
    public static final String ID = "Eve";

    private Date lastStarted;
    private boolean attemptingRestart;

    public Eve(String connectionName, String connectionId) {
        super(connectionName, connectionId);
    }

    public void attemptRestart() {
        WebSocketAPI.send(ID, "RESTART");;
    }
}
