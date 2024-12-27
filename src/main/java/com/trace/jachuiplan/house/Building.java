package com.trace.jachuiplan.house;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Building")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BUILDING_TYPE", nullable = false, columnDefinition = "NUMBER(1)")
    private Integer buildingType;

    @Column(name = "SGGCD", length = 5, nullable = true)
    private String sggcd;

    @Column(name = "UMDNM", length = 20, nullable = true)
    private String umdnm;

    @Column(name = "TOTAL_FLOOR_AR", nullable = true, columnDefinition = "NUMBER(8, 2)")
	private Double totalFloorAr;

    @Column(name = "FLOOR", nullable = true, columnDefinition = "NUMBER(3)")
	private Integer floor;

    @Column(name = "BUILD_YEAR", nullable = true, columnDefinition = "NUMBER(4)")
	private Integer buildYear;

    @Column(name = "DEPOSIT", nullable = true, columnDefinition = "NUMBER(10)")
	private Long deposit;

    @Column(name = "MONTHLY_RENT", nullable = true, columnDefinition = "NUMBER(10)")
	private Long monthlyRent;

    @Column(name = "DEALDATE")
	private LocalDateTime dealdate;

    @Column(name = "JIBUN", length = 20, nullable = true)
    private String jibun;

    @Column(name = "BUILDING_NAME", length = 300, nullable = true)
	private String buildingName;

    @Column(name = "HOUSE_TYPE", length = 30, nullable = true)
	private String houseType;
}
