package signals;

import lombok.*;

import com.mindsmiths.sdk.core.api.Signal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BMIResponse extends Signal {
    private BMIMeasurement request;
    private boolean isObese;
}