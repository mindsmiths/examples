package agents.eve.signals;

import com.mindsmiths.sdk.core.api.Signal;
import com.mindsmiths.sdk.core.db.DataModel;
import lombok.Data;

@Data
public class StartedTask extends Signal {
    private String task;
}
