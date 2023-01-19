package agents;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

import com.mindsmiths.ruleEngine.model.Agent;

import models.Celebrity;


@Data
public class GameAdmin extends Agent {

    List<PlayerAgent> players = new ArrayList<>();
    List<Celebrity> celebrities = new ArrayList<>();

    public GameAdmin() {
        id = "GAME_ADMIN";
    }
}