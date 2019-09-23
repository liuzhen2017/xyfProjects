package com.xingyunfu.ebank.domain.festival;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EbankFestivalDTO {
    private Long id;
    private Integer years;
    private Integer mouths;
    private Integer days;
    private String remark;
}
