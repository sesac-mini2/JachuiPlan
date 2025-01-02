package com.trace.jachuiplan.scrap;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RequiredArgsConstructor
@Controller
public class ScrapApiController {

    private final ScrapService scrapService;

    // 스크랩 등록
    //@PreAuthorize("isAuthenticated()")
    @PostMapping("/scrap/{regioncdId}")
    @ResponseBody
    public ResponseEntity<Boolean> createScrapRegioncd(@PathVariable("regioncdId") Long regioncdId, @AuthenticationPrincipal UserDetails userDetails){
//        scrapService.addScrap(regioncdId, userDetails.getUsername());
        Boolean result = scrapService.addScrap(regioncdId, "user99");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 스크랩 삭제
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/scrap/{regioncdId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteScrapRegioncd(@PathVariable("regioncdId") Long regioncdId, @AuthenticationPrincipal UserDetails userDetails) {
//        scrapService.removeScrap(regioncdId, userDetails.getUsername());
        Boolean result = scrapService.removeScrap(regioncdId, "user99");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/scrap/{regioncdId}")
    public ResponseEntity<Boolean> isScrapRegioncd(@PathVariable("regioncdId") Long regioncdId, @AuthenticationPrincipal UserDetails userDetails){
//        Scrap scrap = scrapService.getScrap(regioncdId, userDetails.getUsername());
        Scrap scrap = scrapService.getScrap(regioncdId, "user99");
        Boolean isScraped = scrap != null;
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(isScraped);
    }
}
