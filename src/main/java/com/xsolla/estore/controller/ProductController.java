package com.xsolla.estore.controller;

import com.sun.istack.NotNull;
import com.xsolla.estore.dto.ProductDto;
import com.xsolla.estore.model.Product;
import com.xsolla.estore.model.Result;
import com.xsolla.estore.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiOperation(value = "Get product by ID or SKU")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product is returned"),
            @ApiResponse(responseCode = "400", description = "Search can't be performed as ID & SKU params are null"),
            @ApiResponse(responseCode = "404", description = "Can't find product")
    })
    public ResponseEntity<?> getProduct(@Parameter(description = "ID of product to search") final Long id,
                                        @Parameter(description = "SKU of product to search") final Long sku) {
        if (Objects.isNull(id) && Objects.isNull(sku)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Search can't be performed as ID & SKU params are null");
        }
        final Optional<Product> product = productService.getProduct(id, sku);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get().getId());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find product");
        }
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "Get all products", notes = "By default products are sorted by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products are returned")
    })
    public Page<Product> getAllProducts(@RequestParam(required=false,defaultValue="false") @Parameter(description = "Sort by price") final boolean sortByPrice,
                                        @RequestParam(required=false,defaultValue="false") @Parameter(description = "Sort by type") final boolean sortByType,
                                        @RequestParam(required=false,defaultValue="0") @Parameter(description = "Num of page to return. Default is 0") final int page,
                                        @RequestParam(required=false,defaultValue="25") @Parameter(description = "Num of results on page. Default is 25") final int size) {
        return productService.getAllProducts(sortByPrice, sortByType, page, size);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added product is returned"),
            @ApiResponse(responseCode = "400", description = "Provided data is bad")
    })
    public ResponseEntity<?> addProduct(@RequestBody @NotNull @Parameter(description = "Product to add") ProductDto product) {
        final Result result = productService.addProduct(product);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getProduct());
        } else {
            return ResponseEntity.status(result.getHttpStatus()).body(result.getMessage());
        }
    }

    @PostMapping("/delete")
    @ApiOperation(value = "Delete the product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added product is returned"),
            @ApiResponse(responseCode = "404", description = "Can't find product by provided ID or SKU")
    })
    public ResponseEntity<?> deleteProduct(@RequestParam(required=false) @Parameter(description = "ID of product to delete") final Long id,
                                           @RequestParam(required=false) @Parameter(description = "SKU of product to delete") final Long sku) {
        final Optional<Product> product = productService.getProduct(id, sku);
        if (product.isPresent()) {
            productService.deleteProduct(product.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "Update the product", notes = "As SKU should be unique, the new SKU shouldn't present in system!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product is updated successfully"),
            @ApiResponse(responseCode = "400", description = "Provided data is bad"),
            @ApiResponse(responseCode = "404", description = "Can't find product by provided ID or SKU to update")
    })
    public ResponseEntity<?> updateProduct(@RequestParam(required=false) @Parameter(description = "ID of product to update") final Long id,
                                           @RequestParam(required=false) @Parameter(description = "SKU of product to update") final Long sku,
                                           @RequestBody final ProductDto productDto) {
        if (Objects.isNull(productDto)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product is not provided!");
        }
        final Result result = productService.updateProduct(id, sku, productDto);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getProduct());
        } else {
            return ResponseEntity.status(result.getHttpStatus()).body(result.getMessage());
        }
    }
}
