package agents.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Character {
    private String text;
    private String imageLink;

    public static List<Character> characters = List.of(
        new Character(
            "Hello! You have a %d-year-old son Olaf at home who is %d cm tall. " +
                "Normal weight at that age is around %d to %d kg.\n" +
                "You can update me on his weight and I will tell you if he has obesity issues.",
            "https://bmiguidance.blob.core.windows.net/media/TelegramAdapterMedia/boy1.png"),
        new Character(
            "Welcome! You are a parent to a %d-year-old daughter Ava. She is %d cm tall. " +
                "Her normal weight range is around %d to %d kg.\n" +
                "We can track her weight together to see if she has obesity issues.",
            "https://bmiguidance.blob.core.windows.net/media/TelegramAdapterMedia/girl1.png"),
        new Character(
            "Hello! Your %d-year-old kiddo Mr.Chewy is %d cm tall. " +
                "Kids his age normally weigh %d-%d kg.\n" +
                "Keep me posted on his weight, and I will help you keep it in check.",
            "https://bmiguidance.blob.core.windows.net/media/TelegramAdapterMedia/boy2.png"),
        new Character(
            "Hi! You have a %d-year-old step-daughter Cinderella that is %d cm tall. " +
                "Her normal weight range is around %d to %d kg.\n" +
                "You can check her health by sending me her weight regularly.",
            "https://bmiguidance.blob.core.windows.net/media/TelegramAdapterMedia/girl2.png"),
        new Character(
            "Hey there! You have a %d-year-old son named X Æ A-12. He is %d cm tall. " +
                "Normally he should weigh between %d and %d kg.\n" +
                "Update me regularly on his weight and I will tell you if he is obese.",
            "https://bmiguidance.blob.core.windows.net/media/TelegramAdapterMedia/boy3.png"),

        new Character(
            "Hello! You have a %d-year-old son George who is %d cm tall. " +
                "Normal weight at that age is around %d to %d kg.\n" +
                "You can update me on his weight and we can keep track of his health.",
            "https://bmiguidance.blob.core.windows.net/media/TelegramAdapterMedia/boy4.png"),
        new Character(
            "Welcome! Your beloved daughter Pearl is %d years old and %d cm tall. " +
                "Her normal weight range is around %d to %d kg.\n" +
                "We can track her weight together to see if she has obesity issues.",
            "https://bmiguidance.blob.core.windows.net/media/TelegramAdapterMedia/girl3.png"),
        new Character(
            "Hi there! You are a proud parent of a %d-year-old daughter Ariel. She is %d cm tall. " +
                "Her normal weight would be around %d to %d kg.\n" +
                "You can keep track of her development and health by sending me her weight.",
            "https://bmiguidance.blob.core.windows.net/media/TelegramAdapterMedia/girl4.png"),
        new Character(
            "Hey! You are a loving parent of a %d-year-old daughter Scout. She is %d cm tall. " +
                "She should normally weigh %d to %d kg.\n" +
                "We can track her weight together to see if there are any issues.",
            "https://bmiguidance.blob.core.windows.net/media/TelegramAdapterMedia/girl5.png")
    );
}
