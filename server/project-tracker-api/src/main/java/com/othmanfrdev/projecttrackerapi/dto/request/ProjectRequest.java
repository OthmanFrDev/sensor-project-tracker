package com.othmanfrdev.projecttrackerapi.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ProjectRequest {
    private Long categoryId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long budgetId;
    private Long userId;
}
