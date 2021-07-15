package com.xsolla.estore.service.impl;

import com.xsolla.estore.model.Product;
import com.xsolla.estore.model.Type;
import com.xsolla.estore.repository.ProductRepository;
import com.xsolla.estore.service.ImportService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ImportServiceImpl implements ImportService {

    private final ProductRepository productRepository;

    public ImportServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void importData() {
        for (int i = 0; i < 5000; i++) {
            Random rnd = new Random();
            final Product product = new Product((long) i, "product_"+i, Type.values()[rnd.nextInt(3)], (long)(i*2));
            productRepository.save(product);
        }
    }
}
