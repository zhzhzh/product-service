package com.demo.productservice.controller;

import com.demo.productservice.entity.Product;
import com.demo.productservice.entity.ProductDaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductDaoService productDaoService;

    public ProductController(ProductDaoService productDaoService) {
        this.productDaoService = productDaoService;
    }

    // GET /products
    @GetMapping("/products")
    public List<Product> retrieveAllProducts() {
        return productDaoService.findAll();
    }

    // GET /products/{productCode}
    @GetMapping("/products/{productCode}")
    public Product retrieveProductWithCode(@PathVariable int productCode) {
        return productDaoService.findProductWithCode(productCode);
    }

    // POST /products/
    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        productDaoService.saveProduct(product);
        return product;
    }
}
