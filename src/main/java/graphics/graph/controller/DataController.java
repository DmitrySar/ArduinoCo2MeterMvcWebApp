package graphics.graph.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.UnknownHostException;

@Controller
public class DataController {

    @GetMapping("/")
    public String getSensors() {
        return "index";
    }

}
