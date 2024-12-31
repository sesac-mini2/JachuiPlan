package com.trace.jachuiplan.scrap;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByRegioncdIdAndUsersUno(Long id, Long uno);
}
