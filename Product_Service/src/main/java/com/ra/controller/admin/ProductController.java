package com.ra.controller.admin;


import com.ra.model.dto.Response.CommonResponse;
import com.ra.model.dto.Response.ProductResponse;
import com.ra.model.entity.Category;
import com.ra.service.ICategoryService;
import com.ra.service.IProductService;
import com.ra.model.dto.Request.Product.ProductRequest;
import com.ra.model.entity.Product;
import com.ra.service.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/admin/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private Mapper mapper;

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        List<String> stringList=new ArrayList<>();
        stringList.add("abc");
        stringList.add("xyz");
        stringList.add("def");
        return new ResponseEntity<>(stringList,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<Product> productList = productService.getAll();
        List<ProductResponse> productResponseList=new ArrayList<>();
        for (Product product : productList
        ) {
            productResponseList.add(mapper.productToProductResponse(product));
        }
        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.OK,productResponseList),HttpStatus.OK);
    }


    //Todo: Should I fix this to get All Enable ?
    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "5",name = "limit") int limit,
                                    @RequestParam(defaultValue = "0",name = "page") int page,
                                    @RequestParam(defaultValue = "id",name = "sortBy") String sort,
                                    @RequestParam(defaultValue = "asc",name = "order") String order){
        Pageable pageable;
        if(order.equals("asc")){
            pageable= PageRequest.of(page,limit, Sort.by(sort).ascending());
        }else {
            pageable= PageRequest.of(page,limit,Sort.by(sort).descending());
        }
        List<ProductResponse> productResponseList=new ArrayList<>();
        for (Product product :
                productService.getAll(pageable)) {
            productResponseList.add(mapper.productToProductResponse(product));
        }
        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.OK,productResponseList), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> findCategoryById(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.OK,mapper.productToProductResponse(product)), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody ProductRequest productRequest) {
        Product product=Product.builder()
                .productName(productRequest.getProductName())
                .category(categoryService.findById(productRequest.getCategoryId()))
                .description(productRequest.getDescription())
                .unitPrice(productRequest.getUnitPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .image(productRequest.getImage())
                .build();
        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.CREATED,mapper.productToProductResponse(productService.save(product))),HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> update(@Valid @RequestBody ProductRequest productRequest,@PathVariable Long productId) {
        Product product=productService.findById(productId);
        product.setProductName(productRequest.getProductName());
        product.setCategory(categoryService.findById(productRequest.getCategoryId()));
        product.setDescription(productRequest.getDescription());
        product.setUnitPrice(productRequest.getUnitPrice());
        product.setStockQuantity(product.getStockQuantity());
        product.setImage(productRequest.getImage());
        return new ResponseEntity<>(productService.save(product),HttpStatus.OK);
    }

    @PutMapping("/delete/{productId}")
    public ResponseEntity<?> deleteById(@PathVariable Long productId){
        Product product=productService.findById(productId);
        product.setStatus(false);
        productService.save(product);
        return new ResponseEntity<>("Delete Success",HttpStatus.OK);
    }


}
