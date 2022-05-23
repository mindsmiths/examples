package agents;

import com.mindsmiths.gpt3.GPT3AdapterAPI;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;
import com.mindsmiths.ruleEngine.util.Util;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import models.Personality;

@Getter
@Setter
public class Nola extends Agent {

    private List<String> memory = new ArrayList<>();
    private int MAX_MEMORY = 4;
    private Date lastInteractionTime;
    private boolean pinged;
    private String name;
    Personality personality = Personality.friendlyAI;

    public static List<String> welcomeTexts = List.of(
        "How has your day been so far?",
        "Enjoying the day so far?",
        "How are you doing?",
        "How is your day going?",
        "How have you been today?"
    );

    private void trimMemory() {
        if (memory.size() > MAX_MEMORY + 1)
            memory = memory.subList(memory.size() - 1 - MAX_MEMORY, memory.size());
    }

    public void clearMemory() {
        memory.clear();
    }

    public Nola() {
    }

    public Nola(String connectionName, String connectionId) {
        super(connectionName, connectionId);
    }


    public void sendMessage(String text) {
        String chatId = getConnections().get("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text);
    }

    public void sendWelcomeMessage(String name) {
        String welcomeText = String.format("Hey %s! 😊 %s", name, this.getRandomWelcomeMessage());
        this.sendMessage(welcomeText);
        this.addMessageToMemory(this.getPersonality().getAiName(), welcomeText);
    }

    public void addMessageToMemory(String sender, String text) {
        memory.add(String.format("%s: %s\n", sender, text));
        trimMemory();
    }

    public void addInstruction(String text) {
        memory.add(text + "\n");
        trimMemory();
    }

    public void changePersonality() {
        clearMemory();
        List<Personality> choices = new ArrayList<>(
                Arrays.asList(Personality.values())
        );
        choices.remove(personality);
        personality = Util.randomChoice(choices);
    }


    public String getRandomWelcomeMessage() {
        Random rand = new Random();
        return welcomeTexts.get(rand.nextInt(welcomeTexts.size()));
    }

    public void askGPT3() {
        simpleGPT3Request(
                personality.getInstruction(name) + String.join("\n", memory) + personality.getAiName() + ":",
                personality.getTemp(),
                personality.getResponseLen(),
                List.of(personality.getAiName() + ":", personality.getHumanName(name) + ":")
        );
    }

    public void simpleGPT3Request(String prompt, Double temp, Integer responseLen, List<String> stop) {
        Log.info("Prompt for GPT-3:\n" + prompt);
        GPT3AdapterAPI.complete(
                prompt, // input prompt
                "text-davinci-001", // model
                responseLen, // max tokens
                temp, // temperature
                1.0, // topP
                1, // N
                null, // logprobs
                false, // echo
                stop, // STOP words
                0.6, // presence penalty
                0.0, // frequency penalty
                1, // best of
                null // logit bias
        );
    }
}
