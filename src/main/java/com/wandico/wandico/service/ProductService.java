package com.wandico.wandico.service;

import com.wandico.wandico.entity.OrderSurmmary;
import com.wandico.wandico.entity.Product;
import com.wandico.wandico.entity.ProductionDetails;
import com.wandico.wandico.repo.ProductRepo;
import com.wandico.wandico.repo.ProductionRepo;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductService {
    private ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void saveProduct(Optional<Product>  product) {
        productRepo.save(product.get());

    }

}
