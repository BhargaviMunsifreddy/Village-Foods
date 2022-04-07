package com.application.villageFoods.payload.requests;

import com.application.villageFoods.models.Cart;
import com.application.villageFoods.models.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CartItemRequest {

    @NotNull
    private int productId;


    @NotNull
    private int quantity;

    private Date createdDate;
    @JsonIgnore
    private Product product;

    private double price;

    public CartItemRequest() {

    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

   public double getPrice() {
       return price;
     }

   public void setPrice(double price) {
       this.price = price;
   }

    public CartItemRequest(Cart cart) {
        this.productId = cart.getProduct().getProductId();
        this.quantity = cart.getQuantity();
        this.price= cart.getPrice();
        this.setProduct(cart.getProduct());
    }

}


