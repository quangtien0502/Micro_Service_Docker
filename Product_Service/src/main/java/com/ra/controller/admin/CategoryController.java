package com.ra.controller.admin;

import com.ra.model.dto.Response.CategoryResponse;
import com.ra.model.dto.Response.CommonResponse;
import com.ra.service.CommonService;
import com.ra.service.ICategoryService;
import com.ra.model.dto.Request.Category.CategoryRequest;
import com.ra.model.entity.Category;
import com.ra.service.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
    @RequestMapping("/v1/admin/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private Mapper mapper;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<Category> categoryList = categoryService.getAll();
        List<CategoryResponse> categoryResponseList=new ArrayList<>();
        for (Category category : categoryList
        ) {
            categoryResponseList.add(mapper.categoryToCategoryResponse(category));
        }
        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.OK,categoryResponseList),HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllEnaBle(@RequestParam(defaultValue = "5", name = "limit") int limit,
                                          @RequestParam(defaultValue = "0", name = "page") int page,
                                          @RequestParam(defaultValue = "id", name = "sortBy") String sort,
                                          @RequestParam(defaultValue = "asc", name = "order") String order) {
        Pageable pageable = commonService.pagination(order, page, limit, sort);
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        for (Category category :
                categoryService.getAllEnable(pageable)) {
            categoryResponseList.add(mapper.categoryToCategoryResponse(category));
        }
        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.OK, categoryResponseList), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> findCategoryById(@PathVariable Long categoryId) {
        Category category = categoryService.findById(categoryId);
        return new ResponseEntity<>(mapper.categoryToCategoryResponse(category), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .categoryName(categoryRequest.getCategoryName())
                .description(categoryRequest.getDescription())
                .build();
        Category categoryNew=categoryService.save(category);
        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.CREATED,categoryNew), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> update(@PathVariable Long categoryId, @RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.findById(categoryId);
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setDescription(categoryRequest.getDescription());
        return new ResponseEntity<>(mapper.categoryToCategoryResponse(categoryService.save(category)), HttpStatus.OK);
    }

    @PutMapping("/delete/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable Long categoryId) {
        Category category = categoryService.findById(categoryId);
        category.setStatus(false);
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.OK);
    }
}
