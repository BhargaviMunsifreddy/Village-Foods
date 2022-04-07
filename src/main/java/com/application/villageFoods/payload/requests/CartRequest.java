package com.application.villageFoods.payload.requests;

import javax.persistence.Column;
import java.util.List;

public class CartRequest {

    private List<CartItemRequest> cartItemRequests;

    @Column(name = "total_price")
    private double totalPrice;

    public CartRequest() {

    }

    public List<CartItemRequest> getCartItemRequests() {
        return cartItemRequests;
    }

    public void setCartItemRequests(List<CartItemRequest> cartItemRequests) {
        this.cartItemRequests = cartItemRequests;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
