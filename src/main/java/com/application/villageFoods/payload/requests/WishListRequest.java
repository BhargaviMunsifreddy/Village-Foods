package com.application.villageFoods.payload.requests;

import java.util.Date;


public class WishListRequest {

    private int productId;

    private Date createdDate;

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
}
