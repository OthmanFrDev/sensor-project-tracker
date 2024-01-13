package com.othmanfrdev.projecttrackerapi.controller;

import com.othmanfrdev.projecttrackerapi.dto.request.BudgetPatchRequest;
import com.othmanfrdev.projecttrackerapi.dto.request.BudgetRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.BudgetResponse;
import com.othmanfrdev.projecttrackerapi.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/budgets")
@CrossOrigin
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }
    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getBudgets(){
        return new ResponseEntity<>(budgetService.getBudgets(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<BudgetResponse> saveBudget(@RequestBody BudgetRequest budgetRequest){
        return new ResponseEntity<>(this.budgetService.saveBudget(budgetRequest),HttpStatus.CREATED);
    }
    @PatchMapping(path = "/{id}")
    public ResponseEntity<BudgetResponse> assignBudget(@PathVariable Long id, @RequestBody BudgetPatchRequest budgetPatchRequest){
        return new ResponseEntity<>(this.budgetService.assignBudget(id, budgetPatchRequest.getProjectId()),HttpStatus.OK);
    }
}
