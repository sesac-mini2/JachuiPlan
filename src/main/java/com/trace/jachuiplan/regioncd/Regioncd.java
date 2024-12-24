package com.trace.jachuiplan.regioncd;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "Regioncd")
public class Regioncd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SIDO_CD", length = 2, nullable = false)
    private String sidoCd;

    @Column(name = "SGG_CD", length = 3, nullable = true)
    private String sggCd;

    @Column(name = "LOCATADD_NM", length = 100, nullable = true)
    private String locataddNm;

    @Column(name = "UMD_CD", length = 3, nullable = true)
    private String umdCd;

    @Column(name = "LATITUDE", nullable = false, precision = 11, scale = 8)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;
}
