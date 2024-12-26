package com.trace.jachuiplan.officeHotel;

import com.trace.jachuiplan.CustomAnnotation.ExactSize;
import com.trace.jachuiplan.building.Building;
import com.trace.jachuiplan.building.BuildingService;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.beans.BulkBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OfficeHotelApiController {
    private final OfficeHotelService officeHotelService;

    @GetMapping("/api/officeHotel/{startyearmonth}/{endyearmonth}/{sggcd}")
    public ResponseEntity<List<OfficeHotel>> getDeals(@PathVariable("startyearmonth") @ExactSize(6) String startyearmonth,
                                                      @PathVariable("endyearmonth") @ExactSize(6) String endyearmonth,
                                                      @PathVariable("sggcd") @ExactSize(6) String sggcd) {
        List<OfficeHotel> officeHotelDeals = officeHotelService.getOfficeHotelDeals(startyearmonth, endyearmonth, sggcd);
        return ResponseEntity.ok()
                .body(officeHotelDeals);
    }

    @GetMapping(value = "/api/officeHotel/{startyearmonth}/{endyearmonth}")
    public ResponseEntity<List<OfficeHotel>> getDealsWithSggList(@PathVariable("startyearmonth") @ExactSize(6) String startyearmonth,
                                                                @PathVariable("endyearmonth") @ExactSize(6) String endyearmonth,
                                                                // 일단은 sggcd부터 리스트로 받는 기능 만들기
                                                                @RequestParam(value = "sggcds") List<@ExactSize(5) String> sggcds) {
        List<OfficeHotel> officeHotelDeals = officeHotelService.getOfficeHotelDeals(startyearmonth, endyearmonth, sggcds);
        return ResponseEntity.ok()
                .body(officeHotelDeals);
    }


    @GetMapping("/api/officeHotel/search")
    public ResponseEntity<List<OfficeHotel>> searchOfficeHotels(
            @RequestParam(name = "rentType", required = false) String rentType,
            @RequestParam(name = "minArea", required = false) Double minArea,
            @RequestParam(name = "maxArea", required = false) Double maxArea,
            @RequestParam(name = "minBuildYear", required = false) Integer minBuildYear,
            @RequestParam(name = "maxBuildYear", required = false) Integer maxBuildYear,
            @RequestParam(name = "minFloor", required = false) Integer minFloor,
            @RequestParam(name = "maxFloor", required = false) Integer maxFloor) {

        List<OfficeHotel> officeHotels = officeHotelService.findOfficeHotelsByCriteria(
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
