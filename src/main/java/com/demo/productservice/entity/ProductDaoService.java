package com.demo.productservice.entity;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class ProductDaoService {

    private static List<Product> products = new ArrayList<>();
    private static int productsCount = 0;

    static {
        products.add(new Product(++productsCount, "Product 01", 1, 1, LocalDate.now()));
        products.add(new Product(++productsCount, "Product 02", 2, 3, LocalDate.now()));
    }

    public List<Product> findAll() {
        return products;
    }

    public Product findProductWithCode(int productCode) {
        Predicate<? super Product> predicate = product -> product.getProductCode().equals(productCode);
        return products.stream().filter(predicate).findFirst().get();
    }

    public Product saveProduct(Product product) {
        product.setId(++productsCount);
        products.add(product);
        return product;
    }



}
