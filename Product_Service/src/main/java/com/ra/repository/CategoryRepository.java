package com.ra.repository;

import com.ra.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Page<Category> findCategoriesByStatus(Boolean status, Pageable pageable);

    Boolean existsCategoryByCategoryName(String name);
}
