package com.trace.jachuiplan.officeHotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfficeHotelRepository extends JpaRepository<OfficeHotel, Long> {
    // 시군구코드 (5자리)와 시작년월 종료년월을 받아서 기간 내 거래 내역을 불러옴
    @Query("SELECT o FROM OfficeHotel o " +
            "WHERE o.dealdate >= TO_DATE(:startyearmonth, 'YYYYMM') " +
            "AND o.dealdate < ADD_MONTHS(TO_DATE(:endyearmonth, 'YYYYMM'), 1) " +
            "AND o.sggcd = :sggcd")
    List<OfficeHotel> getOfficeHotelDeals(@Param("startyearmonth") String startyearmonth,
                                          @Param("endyearmonth") String endyearmonth,
                                          @Param("sggcd") String sggcd);
    // 시군구코드 (5자리) 목록과 시작년월 종료년월을 받아서 기간 내 거래 내역을 불러옴
    @Query("SELECT o FROM OfficeHotel o " +
            "WHERE o.dealdate >= TO_DATE(:startyearmonth, 'YYYYMM') " +
            "AND o.dealdate < ADD_MONTHS(TO_DATE(:endyearmonth, 'YYYYMM'), 1) " +
            "AND o.sggcd IN :sggcds")
    List<OfficeHotel> getOfficeHotelDealsWithSggList(@Param("startyearmonth") String startyearmonth,
                                                     @Param("endyearmonth") String endyearmonth,
                                                     @Param("sggcds") List<String> sggcds);
}
