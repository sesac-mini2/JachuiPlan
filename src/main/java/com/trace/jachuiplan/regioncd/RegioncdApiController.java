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

    @GetMapping("")
    public ResponseEntity<List<Regioncd>> getSidoList() {
        List<Regioncd> regioncds = regioncdService.getSidoList();
        return ResponseEntity.ok()
                .body(regioncds);
    }

    @GetMapping("/{sidocd}")
    public ResponseEntity<List<Regioncd>> getSidoAndSggList(@PathVariable("sidocd") @ExactSize(2) String sidocd) {
        List<Regioncd> regioncds = regioncdService.getSidoAndSggList(sidocd);
        return ResponseEntity.ok()
                .body(regioncds);
    }

    @GetMapping("/{sidocd}/{sggcd}")
    public ResponseEntity<List<Regioncd>> getSggAndUmdList(@PathVariable("sidocd") @ExactSize(2) String sidocd,
            @PathVariable("sggcd") @ExactSize(3) String sggcd) {
        List<Regioncd> regioncds = regioncdService.getSggAndUmdList(sidocd, sggcd);
        return ResponseEntity.ok()
                .body(regioncds);
    }

    // 지도 영역 내 지역들을 조회하는 API
    @GetMapping("/regionsInBounds")
    public ResponseEntity<List<Regioncd>> getRegionsInBounds(
            @RequestParam("north") Double north,
            @RequestParam("east") Double east,
            @RequestParam("south") Double south,
            @RequestParam("west") Double west,
            @RequestParam("level") Integer level) {

        // 레벨을 포함하여 지역들을 조회
        List<Regioncd> regioncds = regioncdService.getRegionsInBounds(north, east, south, west, level);
        return ResponseEntity.ok(regioncds);
    }

    @GetMapping("/id/{regioncdId}")
    public ResponseEntity<List<RegioncdDTO>> getSggcdAndUmdnm(@PathVariable("regioncdId") String regioncdId) {
        List<RegioncdDTO> regioncds = regioncdService.getRegionById(regioncdId);
        return ResponseEntity.ok()
                .body(regioncds);
    }
}
