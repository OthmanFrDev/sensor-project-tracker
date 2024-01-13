package com.othmanfrdev.projecttrackerapi.service;

import com.othmanfrdev.projecttrackerapi.dto.request.CategoryRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategory();
    CategoryResponse saveCategory(CategoryRequest categoryRequest);
}
