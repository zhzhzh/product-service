package com.demo.productservice.jpa;

import com.demo.productservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByCategoryCode(Integer categoryCode);

}
