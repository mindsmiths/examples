package agents.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scenario {
    private Integer age;
    private Integer height;
    private Pair<Integer, Integer> normalWeightRange;
}
