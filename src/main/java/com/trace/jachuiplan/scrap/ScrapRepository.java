package com.trace.jachuiplan.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByRegioncdIdAndUsersUno(Long id, Long uno);

    // 스크랩한 읍면동 정보 building
    @Query("SELECT new com.trace.jachuiplan.scrap.ScrapedListDTO(sr.id, sr.locataddNm, ROUND(AVG(o.monthlyRent), 2), ROUND(AVG(o.deposit), 2), COUNT(o.umdnm)) "
            + "FROM Building o "
            + "RIGHT OUTER JOIN (SELECT r.id AS id, r.locataddNm AS locataddNm, CONCAT(r.sidoCd, r.sggCd) AS sggcd "
            + "FROM Scrap s JOIN Regioncd r ON s.regioncd.id = r.id "
            + "WHERE s.users.uno = :uno) sr ON sr.sggcd = o.sggcd "
            + "AND sr.locataddNm LIKE CONCAT('%', o.umdnm, '%') "
            + "AND (( :startYearMonth IS NULL OR (o.dealdate >= TO_DATE(:startYearMonth, 'YYYYMM'))) "
            + "AND ( :endYearMonth IS NULL OR o.dealdate < ADD_MONTHS(TO_DATE(:endYearMonth, 'YYYYMM'), 1))) "
            + "AND ( :rentType IS NULL OR "
            + "( :rentType = '전세' AND o.monthlyRent = 0 ) OR "
            + "( :rentType = '반전세' AND o.monthlyRent IS NOT NULL AND o.deposit > o.monthlyRent * 12 ) OR "
            + "( :rentType = '월세' AND o.monthlyRent IS NOT NULL AND o.deposit <= o.monthlyRent * 12 ) ) "
            + "AND ( :minArea IS NULL OR o.totalFloorAr >= :minArea ) "
            + "AND ( :maxArea IS NULL OR o.totalFloorAr <= :maxArea ) "
            + "AND ( :minBuildYear IS NULL OR o.buildYear >= :minBuildYear ) "
            + "AND ( :maxBuildYear IS NULL OR o.buildYear <= :maxBuildYear ) "
            + "AND ( :minFloor IS NULL OR o.floor >= :minFloor ) "
            + "AND ( :maxFloor IS NULL OR o.floor <= :maxFloor ) "
            + "GROUP BY sr.id, o.umdnm, sr.locataddNm")
    List<ScrapedListDTO> findScrapedBuilding(
            @Param("startYearMonth") String startYearMonth,
            @Param("endYearMonth") String endYearMonth,
            @Param("rentType") String rentType,
            @Param("minArea") Double minArea,
            @Param("maxArea") Double maxArea,
            @Param("minBuildYear") Integer minBuildYear,
            @Param("maxBuildYear") Integer maxBuildYear,
            @Param("minFloor") Integer minFloor,
            @Param("maxFloor") Integer maxFloor,
            @Param("uno") Long uno);

    // 스크랩한 읍면동 정보 officehotel
    @Query("SELECT new com.trace.jachuiplan.scrap.ScrapedListDTO(sr.id, sr.locataddNm, ROUND(AVG(o.monthlyRent), 2), ROUND(AVG(o.deposit), 2), COUNT(o.umdnm)) "
            + "FROM OfficeHotel o "
            + "RIGHT OUTER JOIN (SELECT r.id AS id, r.locataddNm AS locataddNm, CONCAT(r.sidoCd, r.sggCd) AS sggcd "
            + "FROM Scrap s JOIN Regioncd r ON s.regioncd.id = r.id "
            + "WHERE s.users.uno = :uno) sr ON sr.sggcd = o.sggcd "
            + "AND sr.locataddNm LIKE CONCAT('%', o.umdnm, '%') "
            + "AND (( :startYearMonth IS NULL OR (o.dealdate >= TO_DATE(:startYearMonth, 'YYYYMM'))) "
            + "AND ( :endYearMonth IS NULL OR o.dealdate < ADD_MONTHS(TO_DATE(:endYearMonth, 'YYYYMM'), 1))) "
            + "AND ( :rentType IS NULL OR "
            + "( :rentType = '전세' AND o.monthlyRent = 0 ) OR "
            + "( :rentType = '반전세' AND o.monthlyRent IS NOT NULL AND o.deposit > o.monthlyRent * 12 ) OR "
            + "( :rentType = '월세' AND o.monthlyRent IS NOT NULL AND o.deposit <= o.monthlyRent * 12 ) ) "
            + "AND ( :minArea IS NULL OR o.exclu_use_ar >= :minArea ) "
            + "AND ( :maxArea IS NULL OR o.exclu_use_ar <= :maxArea ) "
            + "AND ( :minBuildYear IS NULL OR o.buildYear >= :minBuildYear ) "
            + "AND ( :maxBuildYear IS NULL OR o.buildYear <= :maxBuildYear ) "
            + "AND ( :minFloor IS NULL OR o.floor >= :minFloor ) "
            + "AND ( :maxFloor IS NULL OR o.floor <= :maxFloor ) "
            + "GROUP BY sr.id, o.umdnm, sr.locataddNm")
    List<ScrapedListDTO>findScrapedOfficeHotel(
            @Param("startYearMonth") String startYearMonth,
            @Param("endYearMonth") String endYearMonth,
            @Param("rentType") String rentType,
            @Param("minArea") Double minArea,
            @Param("maxArea") Double maxArea,
            @Param("minBuildYear") Integer minBuildYear,
            @Param("maxBuildYear") Integer maxBuildYear,
            @Param("minFloor") Integer minFloor,
            @Param("maxFloor") Integer maxFloor,
            @Param("uno") Long uno);
}
