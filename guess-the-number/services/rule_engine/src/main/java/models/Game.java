package models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.mindsmiths.sdk.core.db.PrimaryKey;
import com.mindsmiths.sdk.utils.Utils;

import agents.PlayerAgent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import signals.Guess;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game implements Serializable {
    @PrimaryKey
    private String gameId = Utils.randomGenerator();

    Map<String, Guess> guesses = new HashMap<>();
}
