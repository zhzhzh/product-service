package com.demo.productservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Integer id;

    @JsonProperty("product_name")
    private String productName;

    @Column(unique=true)
    @JsonProperty("product_code")
    private Integer productCode;

    @CreatedDate
    @JsonProperty("creation_date")
    private Instant creationDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_code", referencedColumnName="category_code")
    private Category category;


    public Product() {

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

//    public Integer getCategoryCode() {
//        return categoryCode;
//    }
//
//    public void setCategoryCode(Integer categoryCode) {
//        this.categoryCode = categoryCode;
//    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productCode=" + productCode +
                ", creationDate=" + creationDate +
                '}';
    }
}
