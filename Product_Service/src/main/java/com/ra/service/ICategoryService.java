package com.ra.service;

import com.ra.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    List<Category> getAll();
    Page<Category> getAll(Pageable pageable);
    Category save(Category category);
    Category findById(Long id);
    void deleteById(Long id);

    Page<Category> getAllEnable(Pageable pageable);

    Boolean checkDuplicateName(String name);
}
