package com.ra.model.dto.Request.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductRequest {
    private String productName;
    @Min(value = 0,message = "Category Id must bigger than 0")
    @NotNull(message = "Category Id not Null")
    private Long categoryId;
    private String description;
    private Double unitPrice;
    private Integer stockQuantity;
    private String image;
    private Date createdAt;
    private Date updatedAt;
}
