package agents.eve.signals;

import com.mindsmiths.sdk.core.api.Signal;
import com.mindsmiths.sdk.core.db.DataModel;
import lombok.Data;

@Data
@DataModel(serviceName = "eve", eventName = "task_finished")
public class EveFinishedTask extends Signal {
    private String task;
    private Integer rows;
}
