package graphics.graph.controller;

import graphics.graph.entity.ISensor;
import graphics.graph.entity.TemperatureSensor;
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

    @GetMapping("getsensor")
    public List<Double> getSensorValues() {

        List<Double> res = new ArrayList<>();
        int size = temperatures.size() > VALUES_SIZE ? temperatures.size() - VALUES_SIZE : 0;
        temperatures.subList(size, temperatures.size())
                .forEach(t -> res.add(t.getValue()));
        return res;
    }

    @GetMapping("/sens")
    public void getSensorValue(@RequestParam double t) {
        temperatures.add(new TemperatureSensor(t));
    }

}
