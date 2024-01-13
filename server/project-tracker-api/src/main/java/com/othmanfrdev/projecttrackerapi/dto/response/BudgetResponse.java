package com.othmanfrdev.projecttrackerapi.dto.response;

import com.othmanfrdev.projecttrackerapi.entity.Budget;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Builder
@Data
public class BudgetResponse {
    private Long id;
    private Integer amount;
    private Integer mansDay;
    private String projectName;

    public static BudgetResponse from(Budget budget){
        if(Objects.isNull(budget)) return null;
        BudgetResponse budgetResponse = BudgetResponse.builder()
                .id(budget.getId())
                .mansDay(budget.getManDays())
                .amount(budget.getAmount())
                .build();
        if(Objects.nonNull(budget.getAssignedProject())){
            budgetResponse.setProjectName(budget.getAssignedProject().getName());
        }
        return budgetResponse;
    }
}
