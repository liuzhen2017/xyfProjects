package com.xingyunfu.ebank.dto.transfer;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class TransferMergePageQueryDTO {
    @NotNull@Range(min = 0)
    private Integer pageNo;
    @NotNull@Range(min = 5, max = 50)
    private Integer pageSize;
    @NotNull
    private Long startTime = 0L;
    @NotNull
    private Long endTime = Instant.now().getEpochSecond();
    @NotEmpty
    private String status;
    private String accountNo;
}
