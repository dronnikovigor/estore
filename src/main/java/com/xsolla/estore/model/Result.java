package com.xsolla.estore.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {

    private Product product;
    private boolean success;
    private String message;

    public Result(Product product) {
        this.product = product;
        this.success = true;
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
