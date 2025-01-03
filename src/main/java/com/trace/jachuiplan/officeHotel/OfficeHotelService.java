package com.trace.jachuiplan.officeHotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OfficeHotelService {

    private final OfficeHotelRepository officeHotelRepository;

    public List<OfficeHotel> getOfficeHotelDeals(String startYearMonth, String endYearMonth, String sggcd) {
        return officeHotelRepository.getOfficeHotelDeals(startYearMonth, endYearMonth, sggcd);
    }

    public List<OfficeHotel> getOfficeHotelDeals(String startYearMonth, String endYearMonth, List<String> sggcds) {
        return officeHotelRepository.getOfficeHotelDealsWithSggList(startYearMonth, endYearMonth, sggcds);
    }

    // 레포지토리에서 제공하는 findByCriteria 메서드를 호출
    public List<OfficeHotel> findOfficeHotelsByCriteria(String startYearMonth, String endYearMonth, List<String> sggcds,
                                                        String rentType, Double minArea, Double maxArea, Integer minBuildYear,
                                                        Integer maxBuildYear, Integer minFloor, Integer maxFloor) {
        return officeHotelRepository.findByCriteria(startYearMonth, endYearMonth, sggcds, rentType, minArea, maxArea, minBuildYear, maxBuildYear, minFloor, maxFloor);
    }

    public List<OfficeHotelFilterDTO> averageOfficeHotelsByCriteria(String startYearMonth, String endYearMonth, List<String> sggcds,
                                                                    String rentType, Double minArea, Double maxArea, Integer minBuildYear,
                                                                    Integer maxBuildYear, Integer minFloor, Integer maxFloor) {
        return officeHotelRepository.averageByCriteria(startYearMonth, endYearMonth, sggcds, rentType, minArea, maxArea, minBuildYear, maxBuildYear, minFloor, maxFloor);
    }

    public List<OfficeHotelTransitionDTO> transitionOfficeHotelCriteria(String sggcd, String umdnm,
                                                                  String rentType, Double minArea, Double maxArea, Integer minBuildYear,
                                                                  Integer maxBuildYear, Integer minFloor, Integer maxFloor) {
        return officeHotelRepository.averageAndCountByMonthlyAndUmd(sggcd, umdnm, rentType, minArea, maxArea, minBuildYear, maxBuildYear, minFloor, maxFloor);
    }

}
