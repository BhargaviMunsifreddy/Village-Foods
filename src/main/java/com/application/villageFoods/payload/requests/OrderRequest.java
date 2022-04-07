package com.application.villageFoods.payload.requests;


import com.application.villageFoods.models.Order;

import java.util.List;

public class OrderRequest {

    private List<Order> orderList;

    public OrderRequest() {
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}