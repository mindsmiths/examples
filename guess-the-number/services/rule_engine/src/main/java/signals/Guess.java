package signals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.mindsmiths.sdk.core.api.Message;

import models.Celebrity;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guess extends Message {
    Celebrity celebrity;
    int guess;
    int attemptNumber;
}
