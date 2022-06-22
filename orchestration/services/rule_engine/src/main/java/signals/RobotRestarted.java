package signals;

import com.mindsmiths.sdk.core.api.Signal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RobotRestarted extends Signal {
    private Boolean success;
}
