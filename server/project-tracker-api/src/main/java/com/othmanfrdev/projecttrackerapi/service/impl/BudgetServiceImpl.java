package com.othmanfrdev.projecttrackerapi.service.impl;

import com.othmanfrdev.projecttrackerapi.dto.request.BudgetRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.BudgetResponse;
import com.othmanfrdev.projecttrackerapi.entity.Budget;
import com.othmanfrdev.projecttrackerapi.entity.Project;
import com.othmanfrdev.projecttrackerapi.exception.BudgetValidationException;
import com.othmanfrdev.projecttrackerapi.exception.EntityNotFoundException;
import com.othmanfrdev.projecttrackerapi.repository.BudgetRepository;
import com.othmanfrdev.projecttrackerapi.repository.ProjectRepository;
import com.othmanfrdev.projecttrackerapi.service.BudgetService;
import com.othmanfrdev.projecttrackerapi.utils.ValidatorUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final ProjectRepository projectRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository, ProjectRepository projectRepository) {
        this.budgetRepository = budgetRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<BudgetResponse> getBudgets() {
        return this.budgetRepository
                .findAll()
                .stream()
                .map(BudgetResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetResponse saveBudget(BudgetRequest budgetRequest) {
        if(!ValidatorUtils.isBudgetValid(budgetRequest)){
            throw new BudgetValidationException("Amount & mansDay must be gt than 0.");
        }
        Budget budget = Budget
                .builder()
                .amount(budgetRequest.getAmount())
                .manDays(budgetRequest.getMansDay())
                .build();
        return BudgetResponse.from(this.budgetRepository.save(budget));
    }

    @Override
    public BudgetResponse assignBudget(Long budgetId,Long projectId) {
        Project project = this.projectRepository
                .findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Project with %d not found.", projectId)));
        Budget budget = this.budgetRepository
                .findById(budgetId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Budget with %d not found.", budgetId)));
        budget.setAssignedProject(project);
        project.setBudget(budget);
        projectRepository.save(project);
        return BudgetResponse.from(this.budgetRepository.save(budget));
    }
}
