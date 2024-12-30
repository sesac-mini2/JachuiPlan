package com.trace.jachuiplan.regioncd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegioncdRepository extends JpaRepository<Regioncd, Long> {
    // 시도 단위 리스트만 가져오기 위해서는 sggCd가 000인 경우를 찾아야 함.
    // sggCd가 000이 아니라면 "서울특별시 동작구"와 같이 시군구 단위 혹은 그것보다 하위 항목이라는 의미
    @Query("SELECT r FROM Regioncd r WHERE r.sggCd = '000'")
    List<Regioncd> findSido();
    // 시군구 단위 리스트만 가져오기 위해서는 해당하는 sidoCd(예: 서울특별시는 '11')를 기준으로 필터링을 하고
    // 시도 단위 항목을 제거하기 위해 sggCd가 000이 아닌 경우로 한정하며
    // 읍면동 단위 항목 (예: 서울특별시 동작구 흑석동)도 제거하기 위해 umdCd가 '000'인 경우로 한정한다.
    @Query("SELECT r FROM Regioncd r WHERE r.sidoCd = :sidocd AND r.sggCd != '000' AND r.umdCd = '000'")
    List<Regioncd> findSgg(@Param("sidocd") String sidocd);

    //구 선택시 해당 동 리스트 찾기
    @Query("SELECT r FROM Regioncd r WHERE r.sidoCd = :sidocd AND r.sggCd = :sggcd AND r.umdCd != '000'")
    List<Regioncd> findUmd(@Param("sidocd") String sidocd, @Param("sggcd") String sggcd);

    // 지정된 경계 내에 포함되는 지역들을 조회하는 쿼리
    List<Regioncd> findByLatitudeBetweenAndLongitudeBetween(
        Double latMin, Double latMax, Double lngMin, Double lngMax);
}
