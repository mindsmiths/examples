package external.websockets;

import com.mindsmiths.sdk.core.api.BaseMessage;
import com.mindsmiths.sdk.messaging.Messaging;
import lombok.AllArgsConstructor;

import java.io.Serializable;

public class WebSocketAPI {
    private static final String topic = Messaging.getInputTopicName("websocket_adapter");

    public static void send(String connectionId, Serializable payload) {
        BaseMessage message = new BaseMessage("SEND", new Payload(connectionId, payload));
        message.send(topic);
    }

    @AllArgsConstructor
    static class Payload implements Serializable {
        public String connectionId;
        public Serializable payload;
    }
}
