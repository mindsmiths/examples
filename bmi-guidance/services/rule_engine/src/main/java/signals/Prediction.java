package signals;

import lombok.*;

import com.mindsmiths.sdk.core.db.PrimaryKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prediction {
    @PrimaryKey
    private String predictionId;
    private Boolean prediction;
    private Integer modelVersion;
    private Double confidence;
    private BMIMeasurement bmiMeasurement;
    private boolean sentToDoctor;

    public Prediction(String predictionId, BMIMeasurement bmiMeasurement) {
        this.predictionId = predictionId;
        this.bmiMeasurement = bmiMeasurement;
    }
}
