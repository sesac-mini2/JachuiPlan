package com.trace.jachuiplan.officeHotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OfficeHotelService {
    private final OfficeHotelRepository officeHotelRepository;

    public List<OfficeHotel> getOfficeHotelDeals(String sggcd, String yearmonth) {
        return officeHotelRepository.getOfficeHotelDeals(sggcd, yearmonth);
    }
}
