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
}
