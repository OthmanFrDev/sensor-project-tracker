package com.othmanfrdev.projecttrackerapi.dto.response;

import com.othmanfrdev.projecttrackerapi.entity.Category;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryResponse {
    private Long id;
    private String label;

    public static CategoryResponse from(Category category){
        return CategoryResponse
                .builder()
                .id(category.getId())
                .label(category.getLabel())
                .build();
    }
}
