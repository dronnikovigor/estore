package com.xsolla.estore.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class Result {

    private Product product;
    private boolean success;
    private String message;
    private HttpStatus httpStatus;

    public Result(Product product) {
        this.product = product;
        this.success = true;
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Result(boolean success, String message, HttpStatus httpStatus) {
        this.success = success;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
