package com.trace.jachuiplan.regioncd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegioncdRepository extends JpaRepository<Regioncd, Long> {
    // 시도 단위 리스트만 가져오기 위해서는 sggCd가 000인 경우를 찾아야 함.
    // sggCd가 000이 아니라면 "서울특별시 동작구"와 같이 시군구 단위 혹은 그것보다 하위 항목이라는 의미라서 제외
    @Query("SELECT r FROM Regioncd r WHERE r.sggCd = '000'")
    List<Regioncd> findSido();

    // 해당하는 sidoCd(예: 서울특별시는 '11')를 기준으로 필터링을 하고
    // 읍면동 단위 항목 (예: 서울특별시 동작구 흑석동)도 제거하기 위해 umdCd가 '000'인 경우로 한정
    // 해당 sidoCd와 일치하는 시도/시군구 지역 목록을 반환함
    @Query("SELECT r FROM Regioncd r WHERE r.sidoCd = :sidocd AND r.umdCd = '000'")
    List<Regioncd> findSidoAndSgg(@Param("sidocd") String sidocd);

    // 구 선택시 해당 구 + 동 리스트 찾기
    List<Regioncd> findBySidoCdAndSggCd(String sidocd, String sggcd);

    // 지도 경계 내 지역들을 조회하는 쿼리 메소드
    @Query("SELECT r FROM Regioncd r WHERE r.latitude BETWEEN :south AND :north AND r.longitude BETWEEN :west AND :east")
    List<Regioncd> findRegionsInBoundsWithLevel(
            @Param("north") Double north,
            @Param("east") Double east,
            @Param("south") Double south,
            @Param("west") Double west);

    @Query("SELECT new com.trace.jachuiplan.regioncd.RegioncdDTO(r.sidoCd || r.sggCd, r.locataddNm) FROM Regioncd r WHERE r.id = :id")
    List<RegioncdDTO> findRegionById(@Param("id") String id);
}
