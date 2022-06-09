package external.websockets;

import com.mindsmiths.sdk.core.api.Signal;
import com.mindsmiths.sdk.core.db.DataModel;
import lombok.Data;

@Data
@DataModel(serviceName = "websocket_adapter")
public class SocketConnectionClosed extends Signal {
    private String connectionId;
}
