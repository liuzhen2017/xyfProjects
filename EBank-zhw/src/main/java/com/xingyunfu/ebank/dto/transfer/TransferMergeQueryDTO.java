package com.xingyunfu.ebank.dto.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferMergeQueryDTO {
    @NotNull
    private Long startTime = 0L;
    @NotNull
    private Long endTime = Instant.now().getEpochSecond();
    private String status;
    private String accountNo;
}
