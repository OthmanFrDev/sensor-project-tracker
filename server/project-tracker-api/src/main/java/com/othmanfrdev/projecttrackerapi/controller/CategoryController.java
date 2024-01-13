package com.othmanfrdev.projecttrackerapi.controller;

import com.othmanfrdev.projecttrackerapi.dto.request.CategoryRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.CategoryResponse;
import com.othmanfrdev.projecttrackerapi.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        return new ResponseEntity<>(this.categoryService.getAllCategory(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@RequestBody CategoryRequest categoryRequest){
        return new ResponseEntity<>(this.categoryService.saveCategory(categoryRequest), HttpStatus.CREATED);
    }
}
