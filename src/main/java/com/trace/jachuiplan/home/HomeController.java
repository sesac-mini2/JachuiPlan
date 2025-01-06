/// 이재혁
package com.trace.jachuiplan.home;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
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
