package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.sdk.core.db.DataUtils;
import com.mongodb.client.model.Filters;
import lombok.Data;

import java.util.Date;

@Data
public class Smith extends Agent {
    public static String ID = "SMITH";
    private Date lastLog = new Date();

    public Smith() {
        id = ID;
    }

    public static Integer countAgents(String agentType) {
        int agents = 0;

        for(Agent __ : DataUtils.filter(Filters.eq("type", agentType), Agent.class)) {
            agents++;
        }

        return agents;
    }
}
