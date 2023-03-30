package com.demo.productservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Product {
    @Id
    private Integer id;
    private String productName;
    private Integer productCode;
    private Integer categoryCode;
    private LocalDate creationDate;

//    private Category category;

    public Product(Integer id, String productName, Integer productCode, Integer categoryCode, LocalDate creationDate) {
        this.id = id;
        this.productName = productName;
        this.productCode = productCode;
        this.categoryCode = categoryCode;
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductCode() {
        return productCode;
    }

    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }

    public Integer getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(Integer categoryCode) {
        this.categoryCode = categoryCode;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productCode=" + productCode +
                ", categoryCode='" + categoryCode + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
