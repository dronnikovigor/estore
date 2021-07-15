package com.xsolla.estore.repository;

import com.xsolla.estore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(Long sku);
    List<Product> findByOrderByPriceAscTypeAsc();
    List<Product> findByOrderByPriceAsc();
    List<Product> findByOrderByTypeAsc();
}
