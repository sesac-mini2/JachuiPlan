package com.trace.jachuiplan.map;

import groovy.util.logging.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/map")
public class MapController {
    @GetMapping("/")
    public String getInfoBoards() {
        log.info("MAP CONTROLLER");
        return "map/index";
    }
}
