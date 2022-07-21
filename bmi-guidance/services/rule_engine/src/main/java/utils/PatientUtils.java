package utils;

import agents.patient.Scenario;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Random;

public class PatientUtils {

    public static Scenario generateRandomScenario() {
        Random random = new Random();

        int age = random.nextInt(2, 16);
        int height = 88 + (age) * 6 + random.nextInt(-10, 11);
        Pair<Integer, Integer> normalWeightRange = Pair.of(
            BMIUtils.getWeightForHeightAndBMI(height, BMIUtils.normalBMIRangeByAge.get(age).getLeft()),
            BMIUtils.getWeightForHeightAndBMI(height, BMIUtils.normalBMIRangeByAge.get(age).getRight())
        );

        return new Scenario(age, height, normalWeightRange);
    }

    public static boolean isRobot(String chatId) {
        return chatId.startsWith("ROBO-");
    }
}
