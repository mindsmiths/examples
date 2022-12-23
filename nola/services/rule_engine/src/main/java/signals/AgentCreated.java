package signals;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.sdk.core.api.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentCreated extends Message {
    Agent agent;
}
