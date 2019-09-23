package com.xingyunfu.ebank.domain.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EbankTransferForbidDTO {
    private Long id;
    private String accountNo;
    private Long userNo;
    private String userPhone;

    public EbankTransferForbidDTO(String accountNo, Long userNo, String userPhone){
        this.accountNo = accountNo;
        this.userNo = userNo;
        this.userPhone = userPhone;
    }
}
