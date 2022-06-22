package agents.eve.signals;

import com.mindsmiths.sdk.core.api.Signal;
import lombok.Data;

@Data
public class FinishedTask extends Signal {
    private String task;
    private Integer rows;
}
