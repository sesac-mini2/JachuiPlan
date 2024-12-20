package com.trace.jachuiplan.officeHotel;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OfficeHotelFilterDTO {
    private List<@Size(max = 5, min = 5) String> sggcd;
    private Double excluUseArMin;
    private Double excluUseArMax;
    private Integer floorMin;
    private Integer floorMax;
    private Integer buildYearMin;
    private Integer buildYearMax;
}
