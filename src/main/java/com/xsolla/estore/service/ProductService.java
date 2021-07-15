package com.xsolla.estore.service;

import com.xsolla.estore.dto.ProductDto;
import com.xsolla.estore.model.Product;
import com.xsolla.estore.model.Result;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> getProduct(final Long id, final Long sku);

    List<Product> getAllProducts(final boolean sortByPrice, final boolean sortByType);

    Result addProduct(ProductDto product);

    Result updateProduct(final Long id, final Long sku, final ProductDto productDto);

    void deleteProduct(Product product);

}
