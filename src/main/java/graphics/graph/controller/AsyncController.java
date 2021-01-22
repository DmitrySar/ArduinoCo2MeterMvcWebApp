package graphics.graph.controller;

import graphics.graph.entity.ISensor;
import graphics.graph.entity.TemperatureSensor;
import graphics.graph.entity.TvocSensor;
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

    @GetMapping("getsensor")
    public List<Double> getSensorValues() {

        return getTenLastValues(temperatures);
    }

    @GetMapping("gettvoc")
    public List<Double> getTvocValues() {

        return getTenLastValues(tvocs);
    }


    @GetMapping("/sens")
    public void getSensorValue(@RequestParam(defaultValue = "0") double t,
                               @RequestParam(defaultValue = "0") double h,
                               @RequestParam(defaultValue = "0") double tvoc,
                               @RequestParam(defaultValue = "0") double co2) {
//        System.out.printf("t=%.2f, h=%.2f, tvoc=%.2f, co2=%.2f\n", t, h, tvoc, co2);
        temperatures.add(new TemperatureSensor(t));
        tvocs.add(new TvocSensor(tvoc));
    }

    private List<Double> getTenLastValues(List<ISensor> values) {
        List<Double> res = new ArrayList<>();
        int size = values.size() > VALUES_SIZE ? values.size() - VALUES_SIZE : 0;
        values.subList(size, values.size()).forEach(t -> res.add(t.getValue()));
        return res;
    }

}
