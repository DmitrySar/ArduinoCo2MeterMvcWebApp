package graphics.graph.entity;

public class TvocSensor implements ISensor {

    private double value;

    public TvocSensor() {
    }

    public TvocSensor(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
