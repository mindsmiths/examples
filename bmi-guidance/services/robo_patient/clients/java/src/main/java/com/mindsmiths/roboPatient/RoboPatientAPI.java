package com.mindsmiths.roboPatient;

import com.mindsmiths.sdk.core.api.BaseMessage;
import com.mindsmiths.sdk.messaging.Messaging;

import java.io.Serializable;


public class RoboPatientAPI {
    private static final String topic = Messaging.getInputTopicName("robo_patient");


    public static void init(String chatId, int age, int height, double lowWeight, double highWeight) {
        Serializable payload = new InitPayload(chatId, age, height, lowWeight, highWeight);
        new BaseMessage("INIT", payload).send(topic);
    }
}