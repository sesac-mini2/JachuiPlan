package com.trace.jachuiplan.regioncd;

import com.trace.jachuiplan.CustomAnnotation.ExactSize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RegioncdApiController {
    private final RegioncdService regioncdService;

    @GetMapping("/api/regioncd")
    public ResponseEntity<List<Regioncd>> getSidocdList() {
        List<Regioncd> regioncds = regioncdService.getSidocdList();
        return ResponseEntity.ok()
                .body(regioncds);
    }

    @GetMapping("/api/regioncd/{sidocd}")
    public ResponseEntity<List<Regioncd>> getSggList(@PathVariable("sidocd") @ExactSize(2) String sidocd) {
        List<Regioncd> regioncds = regioncdService.getSggList(sidocd);
        return ResponseEntity.ok()
                .body(regioncds);
    }

    @GetMapping("/api/sgg/{sggCd}")
    public ResponseEntity<List<Regioncd>> getRegionsBySggCd(@PathVariable("sggCd") String sggCd) {
       List<Regioncd> regioncds = regioncdService.getRegionsBySggCd(sggCd);
        return ResponseEntity.ok()
                .body(regioncds);
    }

    // 지도 영역 내 동들을 조회하는 API
    @GetMapping("/api/regionsInBounds")
    public ResponseEntity<List<Regioncd>> getRegionsInBounds(
            @RequestParam("north") Double north,
            @RequestParam("east") Double east,
            @RequestParam("south") Double south,
            @RequestParam("west") Double west) {

        // 경계 내의 동들을 조회
        List<Regioncd> regioncds = regioncdService.getRegionsInBounds(north, east, south, west);
        return ResponseEntity.ok(regioncds);
    }
}
