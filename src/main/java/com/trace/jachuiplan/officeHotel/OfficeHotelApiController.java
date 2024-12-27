package com.trace.jachuiplan.officeHotel;

import com.trace.jachuiplan.CustomAnnotation.ExactSize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OfficeHotelApiController {
    private final OfficeHotelService officeHotelService;

    @GetMapping("/api/officeHotel/{startYearMonth}/{endYearMonth}/{sggcd}")
    public ResponseEntity<List<OfficeHotel>> getDeals(@PathVariable("startYearMonth") @ExactSize(6) String startYearMonth,
                                                      @PathVariable("endYearMonth") @ExactSize(6) String endYearMonth,
                                                      @PathVariable("sggcd") @ExactSize(6) String sggcd) {
        List<OfficeHotel> officeHotelDeals = officeHotelService.getOfficeHotelDeals(startYearMonth, endYearMonth, sggcd);
        return ResponseEntity.ok()
                .body(officeHotelDeals);
    }

    @GetMapping(value = "/api/officeHotel/{startYearMonth}/{endYearMonth}")
    public ResponseEntity<List<OfficeHotel>> getDealsWithSggList(@PathVariable("startYearMonth") @ExactSize(6) String startYearMonth,
                                                                @PathVariable("endYearMonth") @ExactSize(6) String endYearMonth,
                                                                // 일단은 sggcd부터 리스트로 받는 기능 만들기
                                                                @RequestParam(value = "sggcds") List<@ExactSize(5) String> sggcds) {
        List<OfficeHotel> officeHotelDeals = officeHotelService.getOfficeHotelDeals(startYearMonth, endYearMonth, sggcds);
        return ResponseEntity.ok()
                .body(officeHotelDeals);
    }

    @GetMapping("/api/officeHotel/search")
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

}
