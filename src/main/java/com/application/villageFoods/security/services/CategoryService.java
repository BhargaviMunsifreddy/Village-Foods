package com.application.villageFoods.security.services;

import com.application.villageFoods.models.Category;
import com.application.villageFoods.payload.requests.CategoryRequest;
import com.application.villageFoods.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    public void createCategory(CategoryRequest categoryRequest) {
        Category category = new Category(categoryRequest.getCategoryName(),
                categoryRequest.getDescription(),
                categoryRequest.getImageUrl());

        categoryRepository.save(category);
    }

    public Category editCategory(CategoryRequest categoryRequest, int id) throws RuntimeException {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));

        category.setCategoryName((categoryRequest.getCategoryName()));
        category.setDescription(categoryRequest.getDescription());
        category.setImageUrl(categoryRequest.getImageUrl());

        final Category updatedCategory = categoryRepository.save(category);

        CategoryRequest categoryResponse = new CategoryRequest();
        categoryResponse.setCategoryName(category.getCategoryName());
        categoryResponse.setDescription(category.getDescription());
        categoryResponse.setImageUrl(category.getImageUrl());

        return categoryRepository.save(category);

    }


}
