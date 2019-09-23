package com.xingyunfu.ebank.dto.transfer;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class TransferMergeClosedDTO {
    @NotEmpty
    private List<String> list;
}
