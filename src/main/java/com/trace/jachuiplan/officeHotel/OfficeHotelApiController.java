/// 이재혁, 김성현, 이화경
package com.trace.jachuiplan.officeHotel;

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
@RequestMapping("/api/officeHotel")
public class OfficeHotelApiController {
    private final OfficeHotelService officeHotelService;

    /// 김성현
    @GetMapping("/search")
    public ResponseEntity<List<OfficeHotel>> searchOfficeHotels(
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

        List<OfficeHotel> officeHotels = officeHotelService.findOfficeHotelsByCriteria(
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

        return ResponseEntity.ok(officeHotels);
    }

    /// 이재혁
    @GetMapping("/average")
    public ResponseEntity<List<OfficeHotelFilterDTO>> averageOfficeHotels(
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

        List<OfficeHotelFilterDTO> officeHotels = officeHotelService.averageOfficeHotelsByCriteria(
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

        return ResponseEntity.ok(officeHotels);
    }

    /// 이화경
    @GetMapping("/transition")
    public ResponseEntity<List<OfficeHotelTransitionDTO>> transitionBuildingCriteria(
            @RequestParam(name = "sggcd") @ExactSize(5) String sggcd,
            @RequestParam(name = "umdnm") String umdnm,
            @RequestParam(name = "rentType", required = false) String rentType,
            @RequestParam(name = "minArea", required = false) Double minArea,
            @RequestParam(name = "maxArea", required = false) Double maxArea,
            @RequestParam(name = "minBuildYear", required = false) Integer minBuildYear,
            @RequestParam(name = "maxBuildYear", required = false) Integer maxBuildYear,
            @RequestParam(name = "minFloor", required = false) Integer minFloor,
            @RequestParam(name = "maxFloor", required = false) Integer maxFloor) {
        List<OfficeHotelTransitionDTO> buildings = officeHotelService.transitionOfficeHotelCriteria(
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
