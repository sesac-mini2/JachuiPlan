package com.trace.jachuiplan.scrap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScrapedListDTO {
    private Long id;
    private String locataddNm;
    private Double avgMonthlyRent;
    private Double avgDeposit;
    private Integer count;

    public ScrapedListDTO(Number id, String locataddNm, Number avgMonthlyRent, Number avgDeposit, Long count) {
        this.id = id.longValue();
        this.locataddNm = locataddNm;
        this.avgMonthlyRent = avgMonthlyRent != null ? avgMonthlyRent.doubleValue() : 0;
        this.avgDeposit = avgDeposit != null ? avgDeposit.doubleValue() : 0;
        this.count = count != null ? count.intValue() : 0;
    }
}
