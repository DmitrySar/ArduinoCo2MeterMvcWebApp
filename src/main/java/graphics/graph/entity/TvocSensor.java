package graphics.graph.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class TvocSensor implements ISensor {

    @Id
    @GeneratedValue
    private int id;
    private double value;
    private LocalDateTime time;

    public TvocSensor() {
    }

    public TvocSensor(double value) {
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
