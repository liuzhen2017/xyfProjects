package com.xingyunfu.ebank.dto.transfer;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class TransferAccountPageQueryDTO {
    @NotNull
    private Long startTime = 0L;
    @NotNull
    private Long endTime = Instant.now().getEpochSecond();
    @NotNull@Range(min = 5)
    private Integer pageNo;
    @NotNull@Range(min = 10, max = 50)
    private Integer pageSize;
}
