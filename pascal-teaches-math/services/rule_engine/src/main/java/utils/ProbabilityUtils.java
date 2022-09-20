package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProbabilityUtils {

    public static List<Double> generateProbabilityDensityFunction(List<Double> values) {
        List<Double> pdf = new ArrayList<>();
        double sum = 0;

        for (Double value : values) {
            sum += value;
            pdf.add(value);
        }

        // Normalize pdf
        for (int i = 0; i < 10; i++)
            pdf.set(i, pdf.get(i) / sum);

        return pdf;
    }

    public static int sample(List<Double> pdf) {
        double r = new Random().nextDouble();
        for (int i = 0; i < pdf.size(); i++) {
            if (r <= pdf.get(i))
                return i;
            r -= pdf.get(i);
        }
        return pdf.size() - 1;
    }
}
