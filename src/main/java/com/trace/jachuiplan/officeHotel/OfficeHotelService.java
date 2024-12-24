package com.trace.jachuiplan.officeHotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OfficeHotelService {
    private final OfficeHotelRepository officeHotelRepository;

    public List<OfficeHotel> getOfficeHotelDeals(String startyearmonth, String endyearmonth, String sggcd) {
        return officeHotelRepository.getOfficeHotelDeals(startyearmonth, endyearmonth, sggcd);
    }

    public List<OfficeHotel> getOfficeHotelDeals(String startyearmonth, String endyearmonth, List<String> sggcds) {
        return officeHotelRepository.getOfficeHotelDealsWithSggList(startyearmonth, endyearmonth, sggcds);
    }
}
