package com.othmanfrdev.projecttrackerapi.service.impl;

import com.othmanfrdev.projecttrackerapi.dto.request.CategoryRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.CategoryResponse;
import com.othmanfrdev.projecttrackerapi.entity.Category;
import com.othmanfrdev.projecttrackerapi.repository.CategoryRepository;
import com.othmanfrdev.projecttrackerapi.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository
                .findAll()
                .stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse saveCategory(CategoryRequest categoryRequest) {
        Category category=Category
                .builder()
                .label(categoryRequest.getLabel()+"")
                .build();
        return CategoryResponse.from(this.categoryRepository.save(category));
    }
}
