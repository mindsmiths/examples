package agents.models;

import com.mindsmiths.sdk.utils.Utils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Neuron {
    private String id = Utils.randomGenerator();

    private double value = 0;
    private double R_in = 1;
    private double C = 10;

    private Date lastUpdatedAt = new Date();


    public Neuron(double R_in, double C) {
        this.R_in = R_in;
        this.C = C;
    }


    public void decay() {
        double secondsPassed = (new Date().getTime() - lastUpdatedAt.getTime()) / 1000.;
        this.update(0, secondsPassed, 1);
        lastUpdatedAt = new Date();
    }

    public void charge(double amount) {
        this.update(1, amount, this.R_in);
    }

    public void discharge(double amount) {
        this.update(-1, amount, this.R_in);
    }


    protected void update(int target, double amount, double resistance) {
        this.value += (target - this.value) * (1 - Math.exp(-amount / (resistance * this.C)));
    }
}
