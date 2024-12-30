package com.trace.jachuiplan.house;

import com.trace.jachuiplan.CustomAnnotation.ExactSize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HouseApiController {

    private final HouseService houseService;

    @GetMapping("/api/house/search")
    public ResponseEntity<List<House>> searchHouses(
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
        List<House> houses = houseService.findHouseCriteria(
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
        return ResponseEntity.ok(houses);
    }

    @GetMapping("/api/house/average")
    public ResponseEntity<List<HouseFilterDTO>> averageHouses(
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
        List<HouseFilterDTO> houses = houseService.averageHouseCriteria(
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
        return ResponseEntity.ok(houses);
    }
}
