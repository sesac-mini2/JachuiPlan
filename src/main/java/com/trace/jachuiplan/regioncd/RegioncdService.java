/// 이재혁, 김성현
package com.trace.jachuiplan.regioncd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegioncdService {
    private final RegioncdRepository regioncdRepository;

    public List<Regioncd> getSidoList() {
        return regioncdRepository.findSido();
    }

    public List<Regioncd> getSidoAndSggList(String sidocd) {
        return regioncdRepository.findSidoAndSgg(sidocd);
    }

    public List<Regioncd> getSggAndUmdList(String sidocd, String sggcd) {
        return regioncdRepository.findBySidoCdAndSggCd(sidocd, sggcd);
    }

    // 경계 내의 지역들을 조회하는 서비스 메서드
    public List<Regioncd> getRegionsInBounds(Double north, Double east, Double south, Double west, Integer level) {
        return regioncdRepository.findRegionsInBoundsWithLevel(north, east, south, west);

    }

    public List<RegioncdDTO> getRegionById(String id) {
        return regioncdRepository.findRegionById(id);
    }
}
