package utils;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.Map;

public class BMIUtils {

    // https://www.cdc.gov/healthyweight/assessing/bmi/childrens_bmi/about_childrens_bmi.html
    public static final Map<Integer, Pair<Double, Double>> normalBMIRangeByAge = new HashMap<>() {{
        put(2, Pair.of(15., 19.));
        put(3, Pair.of(14.5, 18.5));
        put(4, Pair.of(14., 18.));
        put(5, Pair.of(13.5, 18.));
        put(6, Pair.of(13.5, 18.5));
        put(7, Pair.of(13.5, 19.));
        put(8, Pair.of(13.5, 20.));
        put(9, Pair.of(14., 21.));
        put(10, Pair.of(14.5, 22.));
        put(11, Pair.of(14.5, 23.));
        put(12, Pair.of(15., 24.));
        put(13, Pair.of(15., 25.));
        put(14, Pair.of(16., 26.));
        put(15, Pair.of(16., 27.));
    }};

    public static int getWeightForHeightAndBMI(int height, double bmi) {
        double height_m = height / 100.;
        return (int) Math.round(bmi * height_m * height_m);
    }

    public static boolean isValidWeight(String strWeight) {
        if (NumberUtils.isParsable(strWeight)) {
            Double weight = Double.parseDouble(strWeight);
            return (weight >= 5 && weight <= 300);
        }
        return false;
    }
}
