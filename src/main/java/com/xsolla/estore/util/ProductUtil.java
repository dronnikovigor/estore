package com.xsolla.estore.util;

import com.xsolla.estore.dto.ProductDto;
import com.xsolla.estore.model.BaseEntity;
import com.xsolla.estore.model.Product;

public abstract class ProductUtil {
    public static Product createProduct(ProductDto productDto) {
        final Product product = new Product();
        product.setSku(productDto.getSku());
        product.setName(productDto.getName());
        product.setType(productDto.getType());
        product.setPrice(productDto.getPrice());
        return product;
    }
}
