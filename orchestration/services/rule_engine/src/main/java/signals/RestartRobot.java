package signals;

import com.mindsmiths.sdk.core.api.Signal;
import com.mindsmiths.sdk.utils.Utils;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RestartRobot extends Signal {
    public RestartRobot() {
        setMetadata("robot", null, Utils.getUtcDatetime());
    }
}
