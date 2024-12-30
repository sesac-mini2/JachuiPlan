package com.trace.jachuiplan.house;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HouseService {

    @Autowired
    private final HouseRepository houseRepository;

    // 레포지토리에서 제공하는 findByCriteria 메서드를 호출
    public List<House> findHouseCriteria(String startYearMonth, String endYearMonth, List<String> sggcds,
                                         String rentType, Double minArea, Double maxArea, Integer minBuildYear,
                                         Integer maxBuildYear, Integer minFloor, Integer maxFloor) {
        return houseRepository.findByCriteria(startYearMonth, endYearMonth, sggcds, rentType, minArea, maxArea, minBuildYear, maxBuildYear, minFloor, maxFloor);
    }

    public List<HouseFilterDTO> averageHouseCriteria(String startYearMonth, String endYearMonth, List<String> sggcds,
                                                     String rentType, Double minArea, Double maxArea, Integer minBuildYear,
                                                     Integer maxBuildYear, Integer minFloor, Integer maxFloor) {
        return houseRepository.averageByCriteria(startYearMonth, endYearMonth, sggcds, rentType, minArea, maxArea, minBuildYear, maxBuildYear, minFloor, maxFloor);
    }
}
