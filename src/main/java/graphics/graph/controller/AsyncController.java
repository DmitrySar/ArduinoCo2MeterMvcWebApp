package graphics.graph.controller;

import graphics.graph.entity.*;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RestController
public class AsyncController {

    final private int VALUES_SIZE = 10;
    private List<ISensor> temperatures = new ArrayList<>(VALUES_SIZE);
    private List<ISensor> tvocs = new ArrayList<>();
    private ISensor currentTemperature = new TemperatureSensor();
    private ISensor currentTvocSensor = new TvocSensor();
    private ISensor currentCO2Sensor = new CO2Sensor();
    private ISensor currentHumiditySensor = new HumiditySensor();

    @GetMapping("getsensor")
    public List<Double> getSensorValues() {

        return getTenLastValues(temperatures);
    }

    @GetMapping("gettvoc")
    public List<Double> getTvocValues() {

        return getTenLastValues(tvocs);
    }

    @GetMapping("temperature")
    public ISensor getTemperature() {
        return currentTemperature;
    }

    @GetMapping("tvoc")
    public ISensor getTvoc() {
        return currentTvocSensor;
    }

    @GetMapping("humidity")
    public ISensor getCurrentHumiditySensor() {
        return currentHumiditySensor;
    }

    @GetMapping("co2")
    public ISensor getCurrentCO2Sensor() {
        return currentCO2Sensor;
    }


    @GetMapping("/sens")
    public void getSensorValue(@RequestParam(defaultValue = "0") double t,
                               @RequestParam(defaultValue = "0") double h,
                               @RequestParam(defaultValue = "0") double tvoc,
                               @RequestParam(defaultValue = "0") double co2) {
//        System.out.printf("t=%.2f, h=%.2f, tvoc=%.2f, co2=%.2f\n", t, h, tvoc, co2);
        currentTemperature = new TemperatureSensor(t);
        currentTvocSensor = new TvocSensor(tvoc);
        currentCO2Sensor = new CO2Sensor(co2);
        currentHumiditySensor = new HumiditySensor(h);
        temperatures.add(currentTemperature);
        tvocs.add(currentTvocSensor);
    }

    private List<Double> getTenLastValues(List<ISensor> values) {
        List<Double> res = new ArrayList<>();
        int size = values.size() > VALUES_SIZE ? values.size() - VALUES_SIZE : 0;
        values.subList(size, values.size()).forEach(t -> res.add(t.getValue()));
        return res;
    }

}
