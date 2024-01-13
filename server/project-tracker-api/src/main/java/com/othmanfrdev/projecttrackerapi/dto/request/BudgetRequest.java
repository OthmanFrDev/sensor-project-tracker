package com.othmanfrdev.projecttrackerapi.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BudgetRequest {
    private Integer amount;
    private Integer mansDay;
    private Long projectId;
}
