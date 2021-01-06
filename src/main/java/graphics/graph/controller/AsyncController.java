package graphics.graph.controller;

import graphics.graph.entity.ISensor;
import graphics.graph.entity.TemperatureSensor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RestController
public class AsyncController {
    @GetMapping("getsensor")
    public List<Double> getSensorValues() {
        List<ISensor> temperatures = new ArrayList<>();
        IntStream.range(0, 10).forEach(n -> temperatures.add(new TemperatureSensor()));
        List<Double> res = new ArrayList<>();
        temperatures.forEach(t -> res.add(t.getValue()));
        return res;
    }
}
