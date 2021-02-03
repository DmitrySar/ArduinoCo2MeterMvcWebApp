package graphics.graph.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class HumiditySensor implements ISensor {

    @Id
    @GeneratedValue
    private int id;
    private double value;
    private LocalDateTime time;

    public HumiditySensor() {
    }

    public HumiditySensor(double value) {
        this.value = value;
        this.time = LocalDateTime.now();
    }

    @Override
    public double getValue() {
        return this.value;
    }

    @Override
    public LocalDateTime getTime() {
        return this.time;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
