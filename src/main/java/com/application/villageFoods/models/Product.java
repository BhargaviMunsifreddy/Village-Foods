package com.application.villageFoods.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    @NotBlank
    private String productName;

    @NotBlank
    @Column(name = "image_url")
    private String imageUrl;

    @NotBlank
    private String description;

    private double price;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    //Many-to-one Relationship
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    Category category;

    public Product() {

    }

    public Product(String productName, String imageUrl, String description, double price, int quantity) {
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
        this.quantity = quantity;

    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

