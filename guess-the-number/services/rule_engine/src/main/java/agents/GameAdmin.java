package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import lombok.Data;
import models.Celebrity;

import java.util.ArrayList;
import java.util.List;


@Data
public class GameAdmin extends Agent {

    List<Celebrity> celebrities = new ArrayList<>();

    public GameAdmin() {
        id = "GAME_ADMIN";
    }
}