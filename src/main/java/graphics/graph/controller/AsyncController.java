package graphics.graph.controller;

import graphics.graph.entity.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AsyncController {
    private ISensor currentTemperature = new TemperatureSensor();
    private ISensor currentTvocSensor = new TvocSensor();
    private ISensor currentCO2Sensor = new CO2Sensor();
    private ISensor currentHumiditySensor = new HumiditySensor();

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
    public String getSensorValue(@RequestParam(defaultValue = "0") double t,
                               @RequestParam(defaultValue = "0") double h,
                               @RequestParam(defaultValue = "0") double tvoc,
                               @RequestParam(defaultValue = "0") double co2) {
        System.out.printf("t=%.2f, h=%.2f, tvoc=%.2f, co2=%.2f\n", t, h, tvoc, co2);
        currentTemperature = new TemperatureSensor(t);
        currentTvocSensor = new TvocSensor(tvoc);
        currentCO2Sensor = new CO2Sensor(co2);
        currentHumiditySensor = new HumiditySensor(h);
        return "ok";
    }

}
