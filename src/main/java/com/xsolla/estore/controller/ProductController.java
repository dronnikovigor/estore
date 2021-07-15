package com.xsolla.estore.controller;

import com.xsolla.estore.dto.ProductDto;
import com.xsolla.estore.model.Product;
import com.xsolla.estore.model.Result;
import com.xsolla.estore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get")
    public ResponseEntity<Product> getProduct(final Long id, final Long sku) {
        final Optional<Product> product = productService.getProduct(id, sku);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public Page<Product> getAllProducts(@RequestParam(required=false,defaultValue="false") final boolean sortByPrice,
                                        @RequestParam(required=false,defaultValue="false") final boolean sortByType,
                                        @RequestParam(required=false,defaultValue="0") final int page,
                                        @RequestParam(required=false,defaultValue="25") final int size) {
        return productService.getAllProducts(sortByPrice, sortByType, page, size);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductDto product) {
        final Result result = productService.addProduct(product);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getProduct());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteProduct(final Long id, final Long sku) {
        final Optional<Product> product = productService.getProduct(id, sku);
        if (product.isPresent()) {
            productService.deleteProduct(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(final Long id, final Long sku, @RequestBody final ProductDto productDto) {
        if (Objects.isNull(productDto)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product is not provided!");
        }
        final Result result = productService.updateProduct(id, sku, productDto);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getProduct());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getMessage());
        }
    }
}
