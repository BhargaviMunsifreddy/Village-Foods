package com.application.villageFoods.controller;


import com.application.villageFoods.models.Category;
import com.application.villageFoods.payload.requests.CategoryRequest;
import com.application.villageFoods.payload.responses.MessageResponse;
import com.application.villageFoods.repository.CategoryRepository;
import com.application.villageFoods.security.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping("/add")

    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryRequest categoryRequest) {

        categoryService.createCategory(categoryRequest);

        return ResponseEntity.ok(new MessageResponse("Created Successfully"));


    }

    @GetMapping("/all")

    public ResponseEntity<?> listCategory() {
        List<Category> categories = categoryService.listCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public CategoryRequest getCategoryById(@Valid @PathVariable(value = "id") int id) throws RuntimeException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setCategoryName(category.getCategoryName());
        categoryRequest.setDescription(category.getDescription());
        categoryRequest.setImageUrl(category.getImageUrl());

        return categoryRequest;
    }

    @PutMapping("/update/{id}")

    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryRequest categoryRequest, @PathVariable(value = "id") int id) {

        return ResponseEntity.ok(categoryService.editCategory(categoryRequest, id));
    }

    @DeleteMapping("/delete/{id}")

    public Map<String, Boolean> deleteCategory(@PathVariable(value = "id") int id) throws RuntimeException {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));
        categoryRepository.delete(category);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;


    }
}