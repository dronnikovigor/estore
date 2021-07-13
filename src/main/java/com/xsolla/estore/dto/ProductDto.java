package com.xsolla.estore.dto;

import com.xsolla.estore.model.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long sku;

    private String name;

    private Type type;

    private Long price;

}
