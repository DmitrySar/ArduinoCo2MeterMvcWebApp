package graphics.graph.controller;

import graphics.graph.repository.CO2Repository;
import graphics.graph.repository.HumidityRepository;
import graphics.graph.repository.TemperatureRepository;
import graphics.graph.repository.TvocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.UnknownHostException;
import java.time.LocalDateTime;

@Controller
public class DataController {
    @Autowired
    private CO2Repository co2Repository;
    @Autowired
    private HumidityRepository humidityRepository;
    @Autowired
    private TemperatureRepository temperatureRepository;
    @Autowired
    private TvocRepository tvocRepository;

    @GetMapping("/")
    public String getSensors() {
        return "index";
    }

    @GetMapping("/history")
    public String getHistory(@RequestParam(defaultValue = "2021-02-03T00:00:01")
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                         LocalDateTime start,
                             @RequestParam(defaultValue = "2021-02-03T23:00:01")
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                     LocalDateTime stop,
                             Model model) {
        model.addAttribute("co2List",co2Repository.findByTimeBetween(start, stop));
        model.addAttribute("temperatureList", temperatureRepository.findByTimeBetween(start, stop));
        model.addAttribute("humidityList", humidityRepository.findByTimeBetween(start, stop));
        model.addAttribute("tvocList", tvocRepository.findByTimeBetween(start, stop));

        return "history";
    }

}
