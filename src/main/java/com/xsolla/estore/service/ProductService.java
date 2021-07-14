package com.xsolla.estore.service;

import com.xsolla.estore.model.BaseEntity;
import com.xsolla.estore.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> findProductById(final Long id);
    Optional<Product> findProductBySku(final Long sku);
    List<Product> getAllProducts();
    BaseEntity saveProduct(final Product product);
    void deleteProduct(final Product product);
}
