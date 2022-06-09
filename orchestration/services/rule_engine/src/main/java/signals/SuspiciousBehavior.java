package signals;

import com.mindsmiths.sdk.core.api.Signal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SuspiciousBehavior extends Signal {
    private String robotId;
    private String description;
}
