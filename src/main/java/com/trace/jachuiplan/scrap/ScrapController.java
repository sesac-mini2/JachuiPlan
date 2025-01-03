package com.trace.jachuiplan.scrap;

import com.trace.jachuiplan.CustomAnnotation.ExactSize;
import com.trace.jachuiplan.user.UserService;
import com.trace.jachuiplan.user.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/scrap")
@RequiredArgsConstructor
@Controller
public class ScrapController {

    private final UserService userService;
    private final ScrapService scrapService;

    // 스크랩한 지역
    @GetMapping("/list")
    public String myRegion(@RequestParam(name = "startYearMonth", required = false) @ExactSize(6) String startYearMonth,
                           @RequestParam(name = "endYearMonth", required = false) @ExactSize(6) String endYearMonth,
                           @RequestParam(name = "rentType", required = false) String rentType,
                           @RequestParam(name = "minArea", required = false) Double minArea,
                           @RequestParam(name = "maxArea", required = false) Double maxArea,
                           @RequestParam(name = "minBuildYear", required = false) Integer minBuildYear,
                           @RequestParam(name = "maxBuildYear", required = false) Integer maxBuildYear,
                           @RequestParam(name = "minFloor", required = false) Integer minFloor,
                           @RequestParam(name = "maxFloor", required = false) Integer maxFloor,
                           @RequestParam(name = "type") String type,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model){
        Users users = userService.findByUsername(userDetails.getUsername()).get();
        List<ScrapedListDTO> buildingList = null;
        if(type.equals("building")) {
            buildingList = scrapService.findScrapedBuilding(startYearMonth, endYearMonth, rentType, minArea, maxArea, minBuildYear, maxBuildYear, minFloor, maxFloor, users.getUno());
            model.addAttribute("buildingList", buildingList);
        } else if (type.equals("officeHotel")) {
            buildingList = scrapService.findScrapedOfficeHotel(startYearMonth, endYearMonth, rentType, minArea, maxArea, minBuildYear, maxBuildYear, minFloor, maxFloor, users.getUno());
            model.addAttribute("buildingList", buildingList);
        }
        return "scrap/scrap_list :: scrapFragment";
    }
}
