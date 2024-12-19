package com.trace.jachuiplan.officeHotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfficeHotelRepository extends JpaRepository<OfficeHotel, Long> {
    // 시군구코드 (5자리)와 년월을 받아서 한달짜리 거래 내역 불러옴
    @Query("SELECT o FROM OfficeHotel o " +
            "WHERE o.sggcd = :sggcd " +
            "AND o.dealdate >= TO_DATE(:yearmonth, 'YYYYMM') " +
            "AND o.dealdate < ADD_MONTHS( TO_DATE(:yearmonth, 'YYYYMM'), 1 )")
    List<OfficeHotel> getOfficeHotelDeals(@Param("sggcd") String sggcd, @Param("yearmonth") String yearmonth);
}
