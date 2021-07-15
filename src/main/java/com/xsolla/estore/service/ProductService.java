package com.xsolla.estore.service;

import com.xsolla.estore.dto.ProductDto;
import com.xsolla.estore.model.Product;
import com.xsolla.estore.model.Result;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {

    Optional<Product> getProduct(final Long id, final Long sku);

    Page<Product> getAllProducts(final boolean sortByPrice, final boolean sortByType, final int page, final int size);

    Result addProduct(ProductDto product);

    Result updateProduct(final Long id, final Long sku, final ProductDto productDto);

    void deleteProduct(Product product);

}
