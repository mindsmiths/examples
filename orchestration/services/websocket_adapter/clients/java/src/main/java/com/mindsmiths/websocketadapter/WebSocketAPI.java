package com.mindsmiths.websocketadapter;

import com.mindsmiths.sdk.core.api.BaseMessage;
import com.mindsmiths.sdk.core.api.Signal;
import com.mindsmiths.sdk.messaging.Messaging;
import lombok.AllArgsConstructor;

import java.io.Serializable;

public class WebSocketAPI {
    private static final String topic = Messaging.getInputTopicName("websocket_adapter");

    public static void send(String connectionId, Signal signal) {
        BaseMessage message = new BaseMessage("SEND", new Payload(connectionId, signal));
        message.send(topic);
    }

    @AllArgsConstructor
    static class Payload implements Serializable {
        public String connectionId;
        public Signal signal;
    }
}
