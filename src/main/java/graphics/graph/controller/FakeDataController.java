package graphics.graph.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class FakeDataController {

    List<Integer> temperatures = List.of(25, 26, 30, 18, 12, 34);

    @GetMapping("/")
    public String getStartPage() {
//        model.addAttribute("temperatures", temperatures);
        return "index";
    }

}
