package agents.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    int left, right;
    List<Integer> otherAnswers;

    public int correctAnswer() {
        return left * right;
    }
}
