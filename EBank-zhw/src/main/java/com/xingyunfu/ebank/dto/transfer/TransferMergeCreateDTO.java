package com.xingyunfu.ebank.dto.transfer;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TransferMergeCreateDTO {
    @NotNull
    private Long startTime;
    @NotNull
    private Long endTime;
    @NotEmpty
    private String accountType;
}
