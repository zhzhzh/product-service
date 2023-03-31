package com.demo.productservice.controller;

import com.demo.productservice.entity.Product;
import com.demo.productservice.exception.ProductNotFoundException;
import com.demo.productservice.jpa.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductResource {

    private ProductRepository productRepository;

    public ProductResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // GET /products
    @GetMapping("/products")
    public List<Product> retrieveAllProducts() {
        return productRepository.findAll();
    }

    // GET /products/{productCode}
    @GetMapping("/products/{productCode}")
    public Product retrieveProductWithCode(@PathVariable int productCode) {
        Product product = productRepository.findByProductCode(productCode);
        if (product == null) {
            throw new ProductNotFoundException("product code " + productCode);
        }
        return product;
    }

    // POST /products/
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{productCode}")
                .buildAndExpand(savedProduct.getProductCode())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
