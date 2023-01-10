package signals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.mindsmiths.sdk.core.api.Message;

import agents.PlayerAgent;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerJoined extends Message {
    PlayerAgent player;
}