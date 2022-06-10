package signals;

import com.mindsmiths.sdk.core.api.Signal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RobotCrashed extends Signal {
    private String robotId;
}
