package com.ra.controller.PermitAll;

import com.ra.service.ICategoryService;
import com.ra.model.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/permit/categories")
public class CategoryPermit {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("{categoryId}")
    public ResponseEntity<?> getById(@PathVariable Long categoryId) {
        Category category=categoryService.findById(categoryId);
        if(!category.getStatus()){
            throw new RuntimeException("No category Found like this");
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


}
