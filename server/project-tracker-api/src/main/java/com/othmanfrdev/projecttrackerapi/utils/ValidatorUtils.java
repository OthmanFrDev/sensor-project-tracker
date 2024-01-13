package com.othmanfrdev.projecttrackerapi.utils;

import com.othmanfrdev.projecttrackerapi.dto.request.BudgetRequest;
import com.othmanfrdev.projecttrackerapi.dto.request.ProjectRequest;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Objects;

@UtilityClass
public class ValidatorUtils {
    public static boolean isProjectValid(ProjectRequest request){
        return !Objects.nonNull(request.getStartDate()) ||
                !Objects.nonNull(request.getEndDate()) ||
                !isStarDateBeforeEndDate(request.getStartDate(), request.getEndDate());
    }
    public static boolean isStarDateBeforeEndDate(LocalDate startDate, LocalDate endDate){
        return startDate.isBefore(endDate);
    }
    public static boolean isBudgetValid(BudgetRequest budgetRequest){
        return isNumberGtThanOrEqualsZero(budgetRequest.getAmount()) &&
                isNumberGtThanOrEqualsZero(budgetRequest.getMansDay());
    }
    public static boolean isNumberGtThanOrEqualsZero(Integer number){
        return number >= 0;
    }
}
