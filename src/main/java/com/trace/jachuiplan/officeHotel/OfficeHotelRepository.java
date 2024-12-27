package com.trace.jachuiplan.officeHotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfficeHotelRepository extends JpaRepository<OfficeHotel, Long> {
    // 시군구코드 (5자리)와 시작년월 종료년월을 받아서 기간 내 거래 내역을 불러옴
    @Query("SELECT o FROM OfficeHotel o " +
            "WHERE o.dealdate >= TO_DATE(:startYearMonth, 'YYYYMM') " +
            "AND o.dealdate < ADD_MONTHS(TO_DATE(:endYearMonth, 'YYYYMM'), 1) " +
            "AND o.sggcd = :sggcd")
    List<OfficeHotel> getOfficeHotelDeals(@Param("startYearMonth") String startYearMonth,
                                          @Param("endYearMonth") String endYearMonth,
                                          @Param("sggcd") String sggcd);
    // 시군구코드 (5자리) 목록과 시작년월 종료년월을 받아서 기간 내 거래 내역을 불러옴
    @Query("SELECT o FROM OfficeHotel o " +
            "WHERE o.dealdate >= TO_DATE(:startYearMonth, 'YYYYMM') " +
            "AND o.dealdate < ADD_MONTHS(TO_DATE(:endYearMonth, 'YYYYMM'), 1) " +
            "AND o.sggcd IN :sggcds")
    List<OfficeHotel> getOfficeHotelDealsWithSggList(@Param("startYearMonth") String startYearMonth,
                                                     @Param("endYearMonth") String endYearMonth,
                                                     @Param("sggcds") List<String> sggcds);

    // 동적인 조건을 사용하여 OfficeHotel을 필터링하는 JPQL 쿼리
    @Query("SELECT o FROM OfficeHotel o WHERE "
            + "o.dealdate >= TO_DATE(:startYearMonth, 'YYYYMM') "
            + "AND o.dealdate < ADD_MONTHS(TO_DATE(:endYearMonth, 'YYYYMM'), 1) "
            + "AND o.sggcd IN :sggcds "
            + "AND ( :rentType IS NULL OR "
            + "( :rentType = '전세' AND o.monthlyRent = 0 ) OR "
            + "( :rentType = '반전세' AND o.monthlyRent IS NOT NULL AND o.deposit > o.monthlyRent * 12 ) OR "
            + "( :rentType = '월세' AND o.monthlyRent IS NOT NULL AND o.deposit <= o.monthlyRent * 12 ) ) "
            + "AND ( :minArea IS NULL OR o.exclu_use_ar >= :minArea ) "
            + "AND ( :maxArea IS NULL OR o.exclu_use_ar <= :maxArea ) "
            + "AND ( :minBuildYear IS NULL OR o.buildYear >= :minBuildYear ) "
            + "AND ( :maxBuildYear IS NULL OR o.buildYear <= :maxBuildYear ) "
            + "AND ( :minFloor IS NULL OR o.floor >= :minFloor ) "
            + "AND ( :maxFloor IS NULL OR o.floor <= :maxFloor )")
    List<OfficeHotel> findByCriteria(
            @Param("startYearMonth") String startYearMonth,
            @Param("endYearMonth") String endYearMonth,
            @Param("sggcds") List<String> sggcds,
            @Param("rentType") String rentType,
            @Param("minArea") Double minArea,
            @Param("maxArea") Double maxArea,
            @Param("minBuildYear") Integer minBuildYear,
            @Param("maxBuildYear") Integer maxBuildYear,
            @Param("minFloor") Integer minFloor,
            @Param("maxFloor") Integer maxFloor);

    // 각 동별 평균과 거래량을 알려줌
    @Query("SELECT new com.trace.jachuiplan.officeHotel.OfficeHotelFilterDTO(o.umdnm, ROUND(AVG(o.monthlyRent), 2), ROUND(AVG(o.deposit), 2), COUNT(o.umdnm)) FROM OfficeHotel o WHERE "
            + "o.dealdate >= TO_DATE(:startYearMonth, 'YYYYMM') "
            + "AND o.dealdate < ADD_MONTHS(TO_DATE(:endYearMonth, 'YYYYMM'), 1) "
            + "AND o.sggcd IN :sggcds "
            + "AND ( :rentType IS NULL OR "
            + "( :rentType = '전세' AND o.monthlyRent = 0 ) OR "
            + "( :rentType = '반전세' AND o.monthlyRent IS NOT NULL AND o.deposit > o.monthlyRent * 12 ) OR "
            + "( :rentType = '월세' AND o.monthlyRent IS NOT NULL AND o.deposit <= o.monthlyRent * 12 ) ) "
            + "AND ( :minArea IS NULL OR o.exclu_use_ar >= :minArea ) "
            + "AND ( :maxArea IS NULL OR o.exclu_use_ar <= :maxArea ) "
            + "AND ( :minBuildYear IS NULL OR o.buildYear >= :minBuildYear ) "
            + "AND ( :maxBuildYear IS NULL OR o.buildYear <= :maxBuildYear ) "
            + "AND ( :minFloor IS NULL OR o.floor >= :minFloor ) "
            + "AND ( :maxFloor IS NULL OR o.floor <= :maxFloor )"
            + "GROUP BY o.umdnm")
    List<OfficeHotelFilterDTO> averageByCriteria(
            @Param("startYearMonth") String startYearMonth,
            @Param("endYearMonth") String endYearMonth,
            @Param("sggcds") List<String> sggcds,
            @Param("rentType") String rentType,
            @Param("minArea") Double minArea,
            @Param("maxArea") Double maxArea,
            @Param("minBuildYear") Integer minBuildYear,
            @Param("maxBuildYear") Integer maxBuildYear,
            @Param("minFloor") Integer minFloor,
            @Param("maxFloor") Integer maxFloor);

}
