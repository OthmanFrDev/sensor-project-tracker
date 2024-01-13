package com.othmanfrdev.projecttrackerapi.service;

import com.othmanfrdev.projecttrackerapi.dto.request.BudgetRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.BudgetResponse;

import java.util.List;

public interface BudgetService {
    List<BudgetResponse> getBudgets();

    BudgetResponse saveBudget(BudgetRequest budgetRequest);

    BudgetResponse assignBudget(Long budgetId, Long projectId);
}
