/// 이재혁
package com.trace.jachuiplan.building;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BuildingFilterDTO {
    private String umdnm;
    private Double avgMonthlyRent;
    private Double avgDeposit;
    private Integer count;

    public BuildingFilterDTO(String umdnm, Number avgMonthlyRent, Number avgDeposit, Long count) {
        this.umdnm = umdnm;
        this.avgMonthlyRent = avgMonthlyRent != null ? avgMonthlyRent.doubleValue() : null;
        this.avgDeposit = avgDeposit != null ? avgDeposit.doubleValue() : null;
        this.count = count != null ? count.intValue() : null;
    }
}
