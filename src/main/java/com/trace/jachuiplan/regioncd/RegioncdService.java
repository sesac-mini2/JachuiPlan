package com.trace.jachuiplan.regioncd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegioncdService {
    private final RegioncdRepository regioncdRepository;

    public List<Regioncd> getSidocdList() {
        return regioncdRepository.findSido();
    }

    public List<Regioncd> getSggList(String sidocd) {
        return regioncdRepository.findSgg(sidocd);
    }

    public List<Regioncd> getRegionsBySggCd(String sggCd) { return regioncdRepository.findBySggCd(sggCd);}

    // 경계 내의 동들을 조회하는 서비스 메서드
    public List<Regioncd> getRegionsInBounds(Double north, Double east, Double south, Double west) {
        return regioncdRepository.findByLatitudeBetweenAndLongitudeBetween(
                south, north, west, east
        );
    }
}
