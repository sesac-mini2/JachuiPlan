package com.trace.jachuiplan.regioncd;

import com.trace.jachuiplan.CustomAnnotation.ExactSize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/regioncd")
public class RegioncdApiController {
    private final RegioncdService regioncdService;

    @GetMapping("/")
    public ResponseEntity<List<Regioncd>> getSidoList() {
        List<Regioncd> regioncds = regioncdService.getSidoList();
        return ResponseEntity.ok()
                .body(regioncds);
    }

    @GetMapping("/{sidocd}")
    public ResponseEntity<List<Regioncd>> getSggList(@PathVariable("sidocd") @ExactSize(2) String sidocd) {
        List<Regioncd> regioncds = regioncdService.getSggList(sidocd);
        return ResponseEntity.ok()
                .body(regioncds);
    }

    @GetMapping("/{sidocd}/{sggcd}")
    public ResponseEntity<List<Regioncd>> getUmdList(@PathVariable("sidocd") @ExactSize(2) String sidocd,
                                                     @PathVariable("sggcd") @ExactSize(3) String sggcd) {
        List<Regioncd> regioncds = regioncdService.getUmdList(sidocd, sggcd);
        return ResponseEntity.ok()
                .body(regioncds);
    }

    // 지도 영역 내 지역들을 조회하는 API
    @GetMapping("/regionsInBounds")
    public ResponseEntity<List<Regioncd>> getRegionsInBounds(
            @RequestParam("north") Double north,
            @RequestParam("east") Double east,
            @RequestParam("south") Double south,
            @RequestParam("west") Double west) {

        // 경계 내의 지역들을 조회
        List<Regioncd> regioncds = regioncdService.getRegionsInBounds(north, east, south, west);
        return ResponseEntity.ok(regioncds);
    }
}
