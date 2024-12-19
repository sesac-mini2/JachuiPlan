package com.trace.jachuiplan.officeHotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OfficeHotelApiController {
    private final OfficeHotelService officeHotelService;

    @GetMapping("/api/officeHotel/{sggcd}/{yearmonth}")
    public ResponseEntity<List<OfficeHotel>> getDeals(@PathVariable("sggcd") String sggcd, @PathVariable("yearmonth") String yearmonth) {
        List<OfficeHotel> officeHotelDeals = officeHotelService.getOfficeHotelDeals(sggcd, yearmonth);
        return ResponseEntity.ok()
                .body(officeHotelDeals);
    }
}
