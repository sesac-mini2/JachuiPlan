/// 이재혁
package com.trace.jachuiplan.regioncd;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegioncdDTO {
    private String regioncd;
    private String regionKoreanName;

    public RegioncdDTO(String regioncd, String regionKoreanName) {
        this.regioncd = regioncd;
        this.regionKoreanName = regionKoreanName;
    }
}
