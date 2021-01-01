package graphics.graph.controller;

import graphics.graph.entity.ISensor;
import graphics.graph.entity.TemperatureSensor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Controller
public class DataController {

    @GetMapping("/")
    public String getStartPage(Model model) {
        List<ISensor> temperatures = new ArrayList<>();
        IntStream.range(0, 10).forEach(n -> temperatures.add(new TemperatureSensor()));
        model.addAttribute("temperatures", temperatures);
        return "index";
    }

}
