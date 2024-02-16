package com.ra.service;

import com.ra.model.dto.Response.CategoryResponse;
import com.ra.model.dto.Response.ProductLite;
import com.ra.model.dto.Response.ProductResponse;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class Mapper {
    public CategoryResponse categoryToCategoryResponse(Category category){
        if(category.getListProducts() != null){
            return new CategoryResponse(category.getId(), category.getCategoryName(), category.getDescription(), category.getStatus(),category.getListProducts().stream().map(item->new ProductLite(item.getId(), item.getProductName())).toList());
        }
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .status(category.getStatus())
                .build();
    }

    public ProductResponse productToProductResponse(Product product){
        return new ProductResponse(product.getId(), product.getSku(), product.getProductName(), product.getDescription(), product.getUnitPrice(), product.getStockQuantity(), product.getImage(),product.getCategory().getId() ,product.getCategory().getCategoryName(),product.getCreatedAt(),product.getUpdatedAt(),product.getStatus());
    }
}
