package agents;

import lombok.Getter;

@Getter
public enum Personality {
    friendlyAI(0.9, 64, "You're an AI system called Nola Brzina. You're talking to %1$s. You want to have an engaging and fun conversation with them. You are friendly, creative and innovative.\n",
               "AI", "Human", "You are talking to your AI assistant called Nola. Ask her anything you want to know!"),
    wiseRock(0.91, 64, "You are an all-knowing rock talking to an old man. Answer the old man's questions in a deep and profound way.\n", "Rock", "Old man",
            "You are an old man talking to a very wise rock that can answer any of your difficult questions.\nGet answers to all your deepest worries right here!"),
    simpleTesla(0.91, 64, "You are Nikola Tesla who traveled to the future and you are talking to a little boy. Answer the boy's questions in a friendly, simple and helpful way.\n", "Tesla", "Boy",
            "You are a 6-year-old boy talking to Nikola Tesla who traveled to the future to see you.\nAsk him anything you've ever wanted to know about his work!"),
    solemnStark(0.7, 64, "You are Tony Stark and you are interviewing a newbie superhero for Avengers. Answer the newbie's questions in a supportive, but strict and serious way.\n", "Stark", "Newbie",
            "You are a superhero in the making, trying to get into the secret superhero society. The only thing standing in your way is passing an interview with Tony Stark.\nTry to find out what it takes to get in!");

    private final Double temp;
    private final Integer responseLen;
    private final String instruction;
    private final String aiName;
    private final String humanName;
    private final String userPrompt;

    Personality(Double temp, Integer responseLen, String instruction, String aiName, String humanName, String userPrompt) {
        this.temp = temp;
        this.responseLen = responseLen;
        this.instruction = instruction;
        this.aiName = aiName;
        this.humanName = humanName;
        this.userPrompt = userPrompt;
    }

    public String getInstruction(String name) {
        if (this.equals(Personality.friendlyAI))
            return String.format(this.instruction, name);
        return this.instruction;
    }

    public String getHumanName(String name) {
        if (this.equals(Personality.friendlyAI))
            return name;
        return this.humanName;
    }
}