package agents.smith;

import com.mongodb.client.model.Filters;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.sdk.core.db.DataUtils;


public class Smith extends Agent {
    public static String ID = "SMITH";

    public Smith() {
        this.id = ID;
    }

    public static Integer countAgents(String agentType) {
        int agents = 0;

        for(Agent __ : DataUtils.filter(Filters.eq("type", agentType), Agent.class)) {
            agents++;
        }

        return agents;
    }
}
