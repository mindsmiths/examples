package external.websockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.mindsmiths.sdk.core.api.Signal;
import com.mindsmiths.sdk.core.db.DataModel;
import com.mindsmiths.sdk.utils.serialization.Mapper;
import lombok.Data;

@Data
@DataModel(serviceName = "websocket_adapter", eventName = "socket_message")
public class Restarted extends Signal {
    private String connectionId;
    private JsonNode message;

    public <T> T messageAs(Class<T> cls) throws JsonProcessingException {
        return Mapper.getMapper().treeToValue(message, cls);
    }

    public boolean getSuccess() {
        return message.asBoolean();
    }
}
