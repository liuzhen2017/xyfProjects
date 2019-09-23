package com.xingyunfu.ebank.domain.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EbankAccountExtensionDTO {
    private Long id;
    private String accountNo;
    private String userPhone;
    private String superAccountNo;
    private String superPhone;
}
