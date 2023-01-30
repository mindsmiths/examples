package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import lombok.*;

import com.mindsmiths.armory.ArmoryAPI;
import com.mindsmiths.armory.Screen;
import com.mindsmiths.armory.component.CloudSelect;
import com.mindsmiths.armory.component.Description;
import com.mindsmiths.armory.component.Group;
import com.mindsmiths.armory.component.Header;
import com.mindsmiths.armory.component.Input;
import com.mindsmiths.armory.component.SubmitButton;
import com.mindsmiths.armory.component.Title;
import com.mindsmiths.armory.component.Image;
import com.mindsmiths.gpt3.GPT3AdapterAPI;

import com.mindsmiths.ruleEngine.util.Log;
import java.util.List;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class Felix extends Agent {
    String name;
    Integer weight;
    Integer height;
    String workoutPlan;
    List<String> days;
    
    public void showWelcomeScreens() {
        ArmoryAPI.show(
                getConnection("armory"),
                new Screen("welcome")
                        .add(new Title("Hello! I’m Felix and I’m here to help you get as hot as hell! Ready?"))
                        .add(new Image("public/JogaPuppy.png", false))
                        .add(new SubmitButton("buttonPressed", "Cool, let's go!", "askForName")),
                new Screen("askForName")
                        .add(new Header("logo.png", false))
                        .add(new Title("Alright! First, tell me your name?"))
                        .add(new Input("name", "Type your name here", "text"))
                        .add(new SubmitButton("buttonPressed", "Done, next!", "finishWelcome"))
        );
    }
    
    public void showOnboardingScreens() {
        ArmoryAPI.show(
                getConnection("armory"),
                new Screen("startOnboarding")
                        .add(new Title(String.format("Nice to meet you, %s! To make a workout plan just for you, I have a few question.\nReady? 💪", name)))
                        .add(new Image("public/GymPuppy.png", true))
                        .add(new SubmitButton("buttonPressed", "Let's go!", "askForWeight")),
                new Screen("askForWeight")
                        .add(new Header("logo.png", true))
                        .add(new Title("How much do you weigh in kilograms?"))
                        .add(new Input("weight", "Type your weight here", "number"))
                        .add(new SubmitButton("buttonPressed", "Next!", "askForHeight")),
                new Screen("askForHeight")
                        .add(new Header("logo.png", true))
                        .add(new Title("How tall are you in cm?"))
                        .add(new Input("height", "Type your height here", "number"))
                        .add(new SubmitButton("buttonPressed", "Next!", "finishOnboarding"))
        );
    }
               
    public void showSurveyScreens() {
        ArmoryAPI.show(
                getConnection("armory"),
                new Screen("workoutQuestion")
                        .add(new Header("logo.png", true))
                        .add(new Title("Do you workout?"))
                        .group("buttons")
                        .add(new SubmitButton("submityes", "Hell yeah!", "workoutFrequency"))
                        .add(new SubmitButton("submitno", "Not yet", "chooseDays")),
                new Screen("workoutFrequency")
                        .add(new Header("logo.png", true))
                        .add(new Title("How many days a week?"))
                        .group("buttons")
                        .add(new SubmitButton("rarely", "1-2", "chooseDays"))
                        .add(new SubmitButton("sometimes", "3-4", "chooseDays"))
                        .add(new SubmitButton("often", "5 or more", "chooseDays")),
                new Screen("chooseDays")
                        .add(new Header("logo.png", true))
                        .add(new Title(String.format("Okay %s, we are one step away! Choose the days that you are available for a workout?", name)))
                        .add(new CloudSelect("cloud-select").addOption("Monday", "Monday").addOption("Tuesday", "Tuesday").addOption("Wednesday", "Wednesday").addOption("Thursday","Thursday").addOption("Friday", "Friday").addOption("Saturday", "Saturday").addOption("Sunday","Sunday"))
                        .add(new SubmitButton("daysChoosen", "Go on!", "rewardScreen")),
                new Screen("rewardScreen")
                        .add(new Header("logo.png", true))
                        .add(new Title(String.format("Thank you %s for taking your time to talk to me! I will generate your plan in a few moments!", name)))
                        .add(new SubmitButton("buttonPressed", "Cool!", "surveyCompleted"))
        );
    }

    public void showGPT3Response() {
        ArmoryAPI.show(
            getConnection("armory"),
                new Screen ("gptScreen")
                        .add(new Header("logo.png", true))
                        .add(new Title (this.workoutPlan))
                        .add(new SubmitButton("submitTip", "Thanks Felix!", "endScreen")),
                new Screen ("endScreen")
                        .add(new Header("logo.png", true))
                        .add(new Title("You are the best!💜"))
                        .add(new Description("To join our workout group on Discord, here is a <a href='https://discord.com/invite/mindsmiths'>link</a> !"))
        );
    }

    public void askGPT3() {
            String intro = String.format("Recommend a safe workout plan to a person who is %s kg and %s cm tall: do not mention this information and write an advice in second-person perspective \n", weight, height);
            simpleGPT3Request(intro);
    }

    public void simpleGPT3Request(String prompt) {
            Log.info("Prompt for GPT-3:\n" + prompt);
            GPT3AdapterAPI.complete(
                prompt, // input prompt
                "text-davinci-001", // model
                150, // max tokens
                0.9, // temperature
                1.0, // topP
                1, // N
                null, // logprobs
                false, // echo
                List.of("Human:", "Felix:"), // STOP words
                0.6, // presence penalty
                0.0, // frequency penalty
                1, // best of
                null // logit bias
        );
    }
}