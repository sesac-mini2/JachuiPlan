package com.trace.jachuiplan.regioncd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegioncdService {
    private final RegioncdRepository regioncdRepository;

    public List<Regioncd> getSidocdList() {
        List<Regioncd> regioncds = regioncdRepository.findAll();
        regioncds.removeIf(r -> !r.getSggCd().equals("000"));
        return regioncds;
    }

    public List<Regioncd> getSggList(String sidocd) {
        List<Regioncd> sidocds = regioncdRepository.findBySidoCd(sidocd);
        sidocds.removeIf(r -> r.getSggCd().equals("000"));
        sidocds.removeIf(r -> !r.getUmdCd().equals("000"));
        return sidocds;
    }
}
