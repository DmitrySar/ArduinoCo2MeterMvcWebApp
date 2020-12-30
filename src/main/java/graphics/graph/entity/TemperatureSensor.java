package graphics.graph.entity;

import java.util.Random;

public class TemperatureSensor implements ISensor {

    private double value;

    public TemperatureSensor() {
        value = new Random().nextDouble() * 50;
    }

    public TemperatureSensor(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
