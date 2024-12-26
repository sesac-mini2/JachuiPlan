package com.trace.jachuiplan.officeHotel;

import com.trace.jachuiplan.building.Building;
import com.trace.jachuiplan.building.BuildingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OfficeHotelService {

    private final OfficeHotelRepository officeHotelRepository;
    private final BuildingRepository buildingRepository;
    private final EntityManager entityManager;

    public List<OfficeHotel> getOfficeHotelDeals(String startyearmonth, String endyearmonth, String sggcd) {
        return officeHotelRepository.getOfficeHotelDeals(startyearmonth, endyearmonth, sggcd);
    }

    public List<OfficeHotel> getOfficeHotelDeals(String startyearmonth, String endyearmonth, List<String> sggcds) {
        return officeHotelRepository.getOfficeHotelDealsWithSggList(startyearmonth, endyearmonth, sggcds);
    }

    // 레포지토리에서 제공하는 findByCriteria 메서드를 호출
    public List<OfficeHotel> findOfficeHotelsByCriteria(String rentType, Double minArea, Double maxArea, Integer minBuildYear,
                                                        Integer maxBuildYear, Integer minFloor, Integer maxFloor) {
        return officeHotelRepository.findByCriteria(rentType, minArea, maxArea, minBuildYear, maxBuildYear, minFloor, maxFloor);
    }


}
