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

    public List<Regioncd> getSggList(String sidocd) {
        return regioncdRepository.findSgg(sidocd);
    }

    public List<Regioncd> getUmdList(String sidocd, String sggcd) { return regioncdRepository.findUmd(sidocd, sggcd);}

    // 경계 내의 지역들을 조회하는 서비스 메서드
    public List<Regioncd> getRegionsInBounds(Double north, Double east, Double south, Double west) {
        return regioncdRepository.findByLatitudeBetweenAndLongitudeBetween(
                south, north, west, east
        );
    }
}
