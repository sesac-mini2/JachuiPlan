package com.trace.jachuiplan.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping("/home")
    public String home() {
        return "home";
    }
    @GetMapping("/map")
    public String map() {
        return "redirect:/map/";
    }
}
