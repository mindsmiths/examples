package agents.wallE.signals;

import com.mindsmiths.sdk.core.api.Signal;
import com.mindsmiths.sdk.core.db.DataModel;
import lombok.Data;

@Data
@DataModel(serviceName = "walle", eventName = "cleaned")
public class WallECleaned extends Signal {
    private Integer tasks;
}
