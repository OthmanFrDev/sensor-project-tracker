package com.othmanfrdev.projecttrackerapi.dto.response;

import com.othmanfrdev.projecttrackerapi.entity.Project;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ProjectResponse {
    private Long id;
    private String categoryLabel;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private BudgetResponse budget;
    private String createdBy;

    public static ProjectResponse from(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .startDate(project.getStartDate())
                .categoryLabel(project.getCategory() != null ? project.getCategory().getLabel() : null)
                .endDate(project.getEndDate())
                .budget(BudgetResponse.from(project.getBudget()))
                .createdBy(project.getUser().getName())
                .build();
    }
}
