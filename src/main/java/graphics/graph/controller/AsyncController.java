package graphics.graph.controller;

import graphics.graph.entity.*;
import graphics.graph.repository.CO2Repository;
import graphics.graph.repository.HumidityRepository;
import graphics.graph.repository.TemperatureRepository;
import graphics.graph.repository.TvocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {
    @Autowired
    private TemperatureRepository temperatureRepository;
    @Autowired
    private HumidityRepository humidityRepository;
    @Autowired
    private CO2Repository co2Repository;
    @Autowired
    private TvocRepository tvocRepository;
    private TemperatureSensor currentTemperature = new TemperatureSensor();
    private TvocSensor currentTvocSensor = new TvocSensor();
    private CO2Sensor currentCO2Sensor = new CO2Sensor();
    private HumiditySensor currentHumiditySensor = new HumiditySensor();

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
//        System.out.printf("t=%.2f, h=%.2f, tvoc=%.2f, co2=%.2f\n", t, h, tvoc, co2);
        currentTemperature = new TemperatureSensor(t);
        currentTvocSensor = new TvocSensor(tvoc);
        currentCO2Sensor = new CO2Sensor(co2);
        currentHumiditySensor = new HumiditySensor(h);
        saveToDB();
        return "ok";
    }

    private void saveToDB() {
        temperatureRepository.save(currentTemperature);
        humidityRepository.save(currentHumiditySensor);
        co2Repository.save(currentCO2Sensor);
        tvocRepository.save(currentTvocSensor);
    }

}
