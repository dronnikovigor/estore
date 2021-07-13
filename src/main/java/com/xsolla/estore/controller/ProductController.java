package com.xsolla.estore.controller;

import com.xsolla.estore.dto.ProductDto;
import com.xsolla.estore.model.BaseEntity;
import com.xsolla.estore.model.Product;
import com.xsolla.estore.service.ProductService;
import com.xsolla.estore.util.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @RequestMapping("/get")
    public ResponseEntity<Product> getProduct(final Long id, final Long sku) {
        Optional<Product> product = Optional.empty();
        if (Objects.nonNull(id)) {
            product = productService.findProductById(id);
        }
        if (product.isEmpty() && Objects.nonNull(sku)) {
            product = productService.findProductBySku(sku);
        }
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @RequestMapping("/getAll")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @RequestMapping("/add")
    public ResponseEntity<BaseEntity> addProduct(@RequestBody ProductDto product) {
        final Product newProduct = ProductUtil.createProduct(product);
        final BaseEntity savedProduct = productService.saveProduct(newProduct);
        return ResponseEntity.ok(savedProduct);
    }

    @PostMapping
    @RequestMapping("/delete")
    public ResponseEntity<?> deleteProduct() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @RequestMapping("/update")
    public ResponseEntity<?> updateProduct() {
        return ResponseEntity.ok().build();
    }
}
