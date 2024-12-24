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
}
