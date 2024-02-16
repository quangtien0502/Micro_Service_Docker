package com.ra.service;

import com.ra.model.dto.receive.CategoryReceive;
import com.ra.model.dto.receive.CommonReceive;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(url = "http://product-service:8012/v1/admin/categories",name = "Category")
public interface ICategoryService {

    @GetMapping("/getAll")
    CommonReceive<List<CategoryReceive>> getAll();
}
