package com.xingyunfu.ebank.dto.account;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@NoArgsConstructor
public class AccountPageQueryDTO {
    @NotNull
    private Integer pageNo;
    @NotNull
    private Integer pageSize;
    @NotNull
    private Long startTime = 0L;
    @NotNull
    private Long endTime = Instant.now().getEpochSecond();
}
