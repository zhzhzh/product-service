package com.demo.productservice.jpa;

import com.demo.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductCode(Integer productCode);
}
