package com.application.villageFoods.exception;

public class ProductNotFoundException extends IllegalArgumentException {

    public ProductNotFoundException(String msg) {

        super(msg);

    }
}
