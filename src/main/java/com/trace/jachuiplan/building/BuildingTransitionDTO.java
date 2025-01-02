package com.trace.jachuiplan.building;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BuildingTransitionDTO {
    private String umdnm;
    private String yearMonth;
    private Double avgMonthlyRent;
    private Double avgDeposit;
    private Integer count;

    public BuildingTransitionDTO(String umdnm, String yearMonth, Number avgMonthlyRent, Number avgDeposit, Long count) {
        this.umdnm = umdnm;
        this.yearMonth = yearMonth;
        this.avgMonthlyRent = avgMonthlyRent != null ? avgMonthlyRent.doubleValue() : null;
        this.avgDeposit = avgDeposit != null ? avgDeposit.doubleValue() : null;
        this.count = count != null ? count.intValue() : null;
    }
}
