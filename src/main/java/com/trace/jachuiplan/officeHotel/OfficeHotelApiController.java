package com.trace.jachuiplan.officeHotel;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OfficeHotelApiController {
    private final OfficeHotelService officeHotelService;

    @GetMapping("/api/officeHotel/{startyearmonth}/{endyearmonth}/{sggcd}")
    public ResponseEntity<List<OfficeHotel>> getDeals(@PathVariable("startyearmonth") @Size(max = 6, min = 6) String startyearmonth,
                                                      @PathVariable("endyearmonth") @Size(max = 6, min = 6) String endyearmonth,
                                                      @PathVariable("sggcd") @Size(max = 5, min = 5) String sggcd) {
        List<OfficeHotel> officeHotelDeals = officeHotelService.getOfficeHotelDeals(startyearmonth, endyearmonth, sggcd);
        return ResponseEntity.ok()
                .body(officeHotelDeals);
    }

    @PostMapping("/api/officeHotel/{startyearmonth}/{endyearmonth}/{sggcd}")
    public ResponseEntity<List<OfficeHotel>> getDealsPost(@PathVariable("startyearmonth") @Size(max = 6, min = 6) String startyearmonth,
                                                      @PathVariable("endyearmonth") @Size(max = 6, min = 6) String endyearmonth,
                                                      @PathVariable("sggcd") @Size(max = 5, min = 5) String sggcd) {
        List<OfficeHotel> officeHotelDeals = officeHotelService.getOfficeHotelDeals(startyearmonth, endyearmonth, sggcd);
        return ResponseEntity.ok()
                .body(officeHotelDeals);
    }

    @GetMapping(value = "/api/officeHotel/{startyearmonth}/{endyearmonth}")
    public ResponseEntity<List<OfficeHotel>> getDealsWithSggList(@PathVariable("startyearmonth") @Size(max = 6, min = 6) String startyearmonth,
                                                                @PathVariable("endyearmonth") @Size(max = 6, min = 6) String endyearmonth,
                                                                // 일단은 sggcd부터 리스트로 받는 기능 만들기
                                                                @RequestParam(value = "sggcds") List<@Size(max = 5, min = 5) String> sggcds) {
        List<OfficeHotel> officeHotelDeals = officeHotelService.getOfficeHotelDeals(startyearmonth, endyearmonth, sggcds);
        return ResponseEntity.ok()
                .body(officeHotelDeals);
    }
}
