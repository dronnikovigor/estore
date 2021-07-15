package com.xsolla.estore.service.impl;

import com.xsolla.estore.dto.ProductDto;
import com.xsolla.estore.model.Product;
import com.xsolla.estore.model.Result;
import com.xsolla.estore.repository.ProductRepository;
import com.xsolla.estore.service.ProductService;
import com.xsolla.estore.util.ProductUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> getProduct(Long id, Long sku) {
        Optional<Product> product = Optional.empty();
        if (Objects.nonNull(id)) {
            product = productRepository.findById(id);
        }
        if (product.isEmpty() && Objects.nonNull(sku)) {
            product = productRepository.findBySku(sku);
        }
        return product;
    }

    @Override
    public Page<Product> getAllProducts(final boolean sortByPrice, final boolean sortByType, final int page, final int size) {
        List<String> properties = new ArrayList<>();
        if (sortByType) {
            properties.add("type");
        }
        if (sortByPrice) {
            properties.add("price");
        }
        if (properties.isEmpty()) {
            properties.add("id");
        }
        final Sort orders = Sort.by(Sort.Direction.ASC, properties.toArray(new String[0]));
        final PageRequest pageRequest = PageRequest.of(page, size, orders);
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Result addProduct(ProductDto product) {
        final Product newProduct = ProductUtil.createProduct(product);
        final Optional<Product> productBySku = productRepository.findBySku(product.getSku());;
        if (productBySku.isEmpty()) {
            return new Result(productRepository.save(newProduct));
        } else {
            return new Result(false, "Can't add product with such SKU, it's already exists in system!");
        }
    }

    @Override
    public Result updateProduct(Long id, Long sku, ProductDto productDto) {
        final Optional<Product> productByIdOrSku = getProduct(id, sku);
        final Optional<Product> productByNewSku = getProduct(null, productDto.getSku());
        if (productByNewSku.isPresent() && !(productByNewSku.get().getId().equals(id) || productByNewSku.get().getSku().equals(sku))) {
            return new Result(false, "Can't change SKU of product, as it's already exists in system!");
        }
        if (productByIdOrSku.isPresent()) {
            final Product productForUpdate = ProductUtil.createProduct(productDto);
            productForUpdate.setId(productByIdOrSku.get().getId());
            final Product updatedProduct = productRepository.save(productForUpdate);
            return new Result(updatedProduct);
        } else {
            return new Result(false,"Can't find product with such ID or SKU to update!");
        }
    }

    @Override
    public void deleteProduct(final Product product) {
        productRepository.delete(product);
    }
}
