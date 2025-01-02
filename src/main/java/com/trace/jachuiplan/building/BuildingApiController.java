package com.trace.jachuiplan.building;

import com.trace.jachuiplan.CustomAnnotation.ExactSize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/building")
public class BuildingApiController {

    private final BuildingService buildingService;

    @GetMapping("/search")
    public ResponseEntity<List<Building>> searchBuildings(
            @RequestParam(name = "sggcds") List<@ExactSize(5) String> sggcds,
            @RequestParam(name = "startYearMonth") @ExactSize(6) String startYearMonth,
            @RequestParam(name = "endYearMonth") @ExactSize(6) String endYearMonth,
            @RequestParam(name = "rentType", required = false) String rentType,
            @RequestParam(name = "minArea", required = false) Double minArea,
            @RequestParam(name = "maxArea", required = false) Double maxArea,
            @RequestParam(name = "minBuildYear", required = false) Integer minBuildYear,
            @RequestParam(name = "maxBuildYear", required = false) Integer maxBuildYear,
            @RequestParam(name = "minFloor", required = false) Integer minFloor,
            @RequestParam(name = "maxFloor", required = false) Integer maxFloor) {
        List<Building> buildings = buildingService.findBuildingCriteria(
                startYearMonth,
                endYearMonth,
                sggcds,
                rentType,
                minArea,
                maxArea,
                minBuildYear,
                maxBuildYear,
                minFloor,
                maxFloor);
        return ResponseEntity.ok(buildings);
    }

    @GetMapping("/average")
    public ResponseEntity<List<BuildingFilterDTO>> averageBuildings(
            @RequestParam(name = "sggcds") List<@ExactSize(5) String> sggcds,
            @RequestParam(name = "startYearMonth") @ExactSize(6) String startYearMonth,
            @RequestParam(name = "endYearMonth") @ExactSize(6) String endYearMonth,
            @RequestParam(name = "rentType", required = false) String rentType,
            @RequestParam(name = "minArea", required = false) Double minArea,
            @RequestParam(name = "maxArea", required = false) Double maxArea,
            @RequestParam(name = "minBuildYear", required = false) Integer minBuildYear,
            @RequestParam(name = "maxBuildYear", required = false) Integer maxBuildYear,
            @RequestParam(name = "minFloor", required = false) Integer minFloor,
            @RequestParam(name = "maxFloor", required = false) Integer maxFloor) {
        List<BuildingFilterDTO> buildings = buildingService.averageBuildingCriteria(
                startYearMonth,
                endYearMonth,
                sggcds,
                rentType,
                minArea,
                maxArea,
                minBuildYear,
                maxBuildYear,
                minFloor,
                maxFloor);
        return ResponseEntity.ok(buildings);
    }

    @GetMapping("/transition")
    public ResponseEntity<List<BuildingTransitionDTO>> transitionBuildingCriteria(
            @RequestParam(name = "sggcd") @ExactSize(5) String sggcd,
            @RequestParam(name = "umdnm") String umdnm,
            @RequestParam(name = "rentType", required = false) String rentType,
            @RequestParam(name = "minArea", required = false) Double minArea,
            @RequestParam(name = "maxArea", required = false) Double maxArea,
            @RequestParam(name = "minBuildYear", required = false) Integer minBuildYear,
            @RequestParam(name = "maxBuildYear", required = false) Integer maxBuildYear,
            @RequestParam(name = "minFloor", required = false) Integer minFloor,
            @RequestParam(name = "maxFloor", required = false) Integer maxFloor) {
        List<BuildingTransitionDTO> buildings = buildingService.transitionBuildingCriteria(
                sggcd,
                umdnm,
                rentType,
                minArea,
                maxArea,
                minBuildYear,
                maxBuildYear,
                minFloor,
                maxFloor);
        return ResponseEntity.ok(buildings);
    }
}
