package com.xsolla.estore.controller;

import com.xsolla.estore.dto.ProductDto;
import com.xsolla.estore.model.BaseEntity;
import com.xsolla.estore.model.Product;
import com.xsolla.estore.service.ProductService;
import com.xsolla.estore.util.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/get")
    public ResponseEntity<Product> getProduct(final Long id, final Long sku) {
        Optional<Product> product = getProductByIdOrSku(id, sku);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductDto product) {
        final Product newProduct = ProductUtil.createProduct(product);
        final Optional<Product> productBySku = productService.findProductBySku(product.getSku());
        if (productBySku.isEmpty()) {
            final BaseEntity savedProduct = productService.saveProduct(newProduct);
            return ResponseEntity.ok(savedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product with such SKU is already in system!");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteProduct(final Long id, final Long sku) {
        final Optional<Product> product = getProductByIdOrSku(id, sku);
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
        final Optional<Product> productByIdOrSku = getProductByIdOrSku(id, sku);
        final Optional<Product> productByNewSku = productService.findProductBySku(productDto.getSku());
        if (productByNewSku.isPresent() && !(productByNewSku.get().getId().equals(id) || productByNewSku.get().getSku().equals(sku))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't change SKU of product, as it's already exists in system!");
        }
        if (productByIdOrSku.isPresent()) {
            final Product productForUpdate = ProductUtil.createProduct(productDto);
            productForUpdate.setId(productByIdOrSku.get().getId());
            final BaseEntity updatedProduct = productService.saveProduct(productForUpdate);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't find product with such ID or SKU to update!");
        }
    }

    private Optional<Product> getProductByIdOrSku(final Long id, final Long sku) {
        Optional<Product> product = Optional.empty();
        if (Objects.nonNull(id)) {
            product = productService.findProductById(id);
        }
        if (product.isEmpty() && Objects.nonNull(sku)) {
            product = productService.findProductBySku(sku);
        }
        return product;
    }
}
