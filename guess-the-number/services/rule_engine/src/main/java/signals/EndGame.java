package signals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.mindsmiths.sdk.core.api.Message;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndGame extends Message {
    Integer correctAnswer;
}
