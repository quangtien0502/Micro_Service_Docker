package com.ra.model.dto.Request.Category;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategoryRequest {
    @Column(nullable = false,unique = true)
    private String categoryName;
    private String description;
}
