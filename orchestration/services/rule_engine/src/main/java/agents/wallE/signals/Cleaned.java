package agents.wallE.signals;

import com.mindsmiths.sdk.core.api.Signal;
import lombok.Data;

@Data
public class Cleaned extends Signal {
    private Integer tasks;
}
