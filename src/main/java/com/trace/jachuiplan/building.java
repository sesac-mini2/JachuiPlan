package com.trace.jachuiplan;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import oracle.sql.DATE;
import oracle.sql.NUMBER;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUILDNO")
    private Long buildNo;

    @Column(name = "TYPE", nullable = false, columnDefinition = "NUMBER(1)")
    private Integer type;

    @Column(name = "SGGCD", length = 5, nullable = true)
    private String sggCd;

    @Column(name = "UMDNM", length = 20, nullable = true)
    private String umdNm;

    @Column(name = "TOTALFLOORAR", nullable = true, columnDefinition = "NUMBER(8, 2)")
	private Double totalFloorAr;

    @Column(name = "FLOOR", nullable = true, columnDefinition = "NUMBER(3)")
	private Integer floor;

    @Column(name = "BUILDYEAR", nullable = true, columnDefinition = "NUMBER(4)")
	private Integer buildYear;

    @Column(name = "DEPOSIT", nullable = true, columnDefinition = "NUMBER(10)")
	private Long deposit;

    @Column(name = "MONTHLYRENT", nullable = true, columnDefinition = "NUMBER(10)")
	private Long monthlyRent;

    @Column(name = "DEALDATE")
	private LocalDateTime dealDate;

    @Column(name = "BUILDINGNAME", length = 300, nullable = true)
	private String buildingName;

    @Column(name = "HOUSETYPE", length = 30, nullable = true)
	private String houseType;
}
