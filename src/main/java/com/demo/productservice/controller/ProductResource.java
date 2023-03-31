package com.demo.productservice.controller;

import com.demo.productservice.entity.Product;
import com.demo.productservice.exception.ProductNotFoundException;
import com.demo.productservice.jpa.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class ProductResource {

    @Autowired
    private ProductRepository productRepository;

//    // GET /products
//    @GetMapping("/all_products")
//    public List<Product> retrieveAllProducts() {
//        return productRepository.findAll();
//    }

    // GET products (support paging and sortBy one field with order)
    @GetMapping("/products")
    public Page<Product> retrieveProducts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "productCode") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Sort sortOrder = Sort.by(sortBy);
        if (order.startsWith("desc")) {
            sortOrder = sortOrder.descending();
        } else if (order.startsWith("asc")) {
            sortOrder = sortOrder.ascending();
        }

        Pageable paging = PageRequest.of(pageNo, pageSize, sortOrder);
        Page<Product> pagedResult = productRepository.findAll(paging);
        return pagedResult;

    }

    // GET /products/{productCode}
    @GetMapping("/products/{productCode}")
    public Product retrieveProductWithCode(@PathVariable String productCode) {
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
