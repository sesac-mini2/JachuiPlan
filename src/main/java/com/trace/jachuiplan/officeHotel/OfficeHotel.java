package com.trace.jachuiplan.officeHotel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Office_Hotel")
public class OfficeHotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SGGCD", length = 5, nullable = true)
    private String sggcd;

    @Column(name = "UMDNM", length = 20, nullable = true)
    private String umdnm;

    @Column(name = "EXCLU_USE_AR", nullable = true, columnDefinition = "NUMBER(8, 2)")
    private Double exclu_use_ar;

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
}
