package com.wandico.wandico.service;

import com.wandico.wandico.entity.ProductionDetails;
import com.wandico.wandico.repo.ProductionRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdService {
    private ProductionRepo productionRepo;

    public ProdService(ProductionRepo productionRepo) {
        this.productionRepo = productionRepo;
    }

    public ProductionDetails getProductionDetails() {
        return productionRepo.findAll().get(0);

    }

    public void saveProductionDetails(ProductionDetails productionDetails) {
        productionRepo.deleteAll();
        productionRepo.save(productionDetails);

    }

}
