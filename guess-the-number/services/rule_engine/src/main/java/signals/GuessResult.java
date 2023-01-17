package signals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.mindsmiths.sdk.core.api.Message;
import models.EvaluationResult;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuessResult extends Message {
    EvaluationResult result;
}
