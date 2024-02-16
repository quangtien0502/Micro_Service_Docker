package com.ra.model.dto.Response;

import com.ra.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryResponse {
    private Long id;
    private String description;
    private String categoryName;
    private Boolean status;
    private List<ProductLite> product;

    public CategoryResponse(Category category) {
        this.id=category.getId();
        this.categoryName=category.getCategoryName();
        this.status=category.getStatus();
    }
}
