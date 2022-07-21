package signals;

import java.util.List;
import java.util.Random;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.mindsmiths.sdk.core.api.Signal;


@Data
@NoArgsConstructor
public class BMIMeasurement extends Signal {
    private Integer age;
    private Double weight;
    private Integer height;

    public BMIMeasurement(Integer age, Double weight, Integer height) {
        this.age = age;
        this.weight = weight;
        this.height = height;
        checkData();
    }

    public static List<String> invalidValueTexts = List.of(
            "Your child is of age %d and weighs %s. ",
            "I see your child is %d and weighs %s. ",
            "By what you told me, your %d-year-old weighs %s. "
    );

    public static List<String> correctValueTexts = List.of(
            "Can you send me the correct weight?",
            "Please check it and send me the correct weight.",
            "Please send the weight so I can check it."
    );

    public boolean checkAge() {
        return age >= 1 && age <= 20;
    }

    public boolean checkWeight() {
        return weight >= 5 && weight <= 300;
    }

    public boolean checkHeight() {
        return height >= 30 && height <= 230;
    }

    public void checkData() throws ArithmeticException{
        if(weight > 300) {
            throw new ArithmeticException(
                    String.format(this.getInvalidValueTexts(), age, "over 300 kg") + this.getCorrectValueTexts());
        }
        if(weight < 5) {
            throw new ArithmeticException(
                    String.format(this.getInvalidValueTexts(), age, "under 5 kg") + this.getCorrectValueTexts());
        }
         if(!checkAge()) {
             throw new ArithmeticException("We can not process this age. Please input different age.");
         }
         if(!checkWeight()) {
             throw new ArithmeticException("We can not process this weight. Please input different weight.");
         }
         if(!checkHeight()) {
             throw new ArithmeticException("We can not process this height. Please input different height.");
         }
    }

    public String getInvalidValueTexts() {
        Random rand = new Random();
        return invalidValueTexts.get(rand.nextInt(invalidValueTexts.size()));
    }

    public String getCorrectValueTexts() {
        Random rand = new Random();
        return correctValueTexts.get(rand.nextInt(correctValueTexts.size()));
    }

    public Double getBMI() {
        Double heightMeters = height / 100.0;

        return weight / (heightMeters * heightMeters);
    }
}
