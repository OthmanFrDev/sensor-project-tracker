package com.othmanfrdev.projecttrackerapi.service.impl;

import com.othmanfrdev.projecttrackerapi.dto.request.BudgetRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.BudgetResponse;
import com.othmanfrdev.projecttrackerapi.exception.BudgetValidationException;
import com.othmanfrdev.projecttrackerapi.exception.EntityNotFoundException;
import com.othmanfrdev.projecttrackerapi.repository.BudgetRepository;
import com.othmanfrdev.projecttrackerapi.repository.ProjectRepository;
import com.othmanfrdev.projecttrackerapi.service.BudgetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class BudgetServiceImplTest {
    @Autowired
    private BudgetService budgetService;
    @Test
    void saveInvalidBudget() {
        BudgetRequest budgetRequest=BudgetRequest
                .builder()
                .amount(-1)
                .mansDay(-230)
                .build();
        Assertions.assertThrows(BudgetValidationException.class,
                ()->this.budgetService.saveBudget(budgetRequest),
                "Amount & mansDay must be gt than 0.");
    }
    @Test
    void saveValidBudget() {
        BudgetRequest budgetRequest=BudgetRequest
                .builder()
                .amount(50000)
                .mansDay(230)
                .build();
        BudgetResponse budgetResponse = this.budgetService.saveBudget(budgetRequest);
        Assertions.assertNotNull(budgetResponse.getId());
        Assertions.assertNull(budgetResponse.getProjectName());
    }

    @Test
    void assignBudgetWithoutProjectId() {
        BudgetRequest budgetRequest=BudgetRequest
                .builder()
                .amount(2000)
                .mansDay(70)
                .build();
        this.budgetService.saveBudget(budgetRequest);
        Assertions.assertThrows(EntityNotFoundException.class,
                ()->this.budgetService.assignBudget(1L,30L),
                "Project with 30 not found.");
    }
}