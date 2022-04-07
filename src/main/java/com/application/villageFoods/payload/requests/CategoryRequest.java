package com.application.villageFoods.payload.requests;

import javax.validation.constraints.NotBlank;

public class CategoryRequest {

    @NotBlank
    private String categoryName;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
