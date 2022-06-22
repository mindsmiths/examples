package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import lombok.Data;

import java.util.Date;

@Data
public class Smith extends Agent {
    public static String ID = "SMITH";
    private Date lastLog = new Date();

    public Smith() {
        id = ID;
    }
}
