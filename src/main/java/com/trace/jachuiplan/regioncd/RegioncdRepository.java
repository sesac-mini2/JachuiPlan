package com.trace.jachuiplan.regioncd;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegioncdRepository extends JpaRepository<Regioncd, Long> {
    List<Regioncd> findBySidoCd(String sidocd);
}
