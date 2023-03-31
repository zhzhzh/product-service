package com.demo.productservice.controller;

import com.demo.productservice.entity.Category;
import com.demo.productservice.jpa.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryResource {

    @Autowired
    private CategoryRepository categoryRepository;

    // GET products (support paging and sortBy one field with order)
    @GetMapping("/categories")
    public Page<Category> retrieveCategories(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "categoryCode") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Sort sortOrder = Sort.by(sortBy);
        if (order.startsWith("desc")) {
            sortOrder = sortOrder.descending();
        } else if (order.startsWith("asc")) {
            sortOrder = sortOrder.ascending();
        }

        Pageable paging = PageRequest.of(pageNo, pageSize, sortOrder);
        Page<Category> pagedResult = categoryRepository.findAll(paging);
        return pagedResult;

    }
}
