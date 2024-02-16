package com.ra.controller.PermitAll;

import com.ra.service.CommonService;
import com.ra.service.IProductService;
import com.ra.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/permit/products")
public class ProductPermit {

    @Autowired
    private IProductService productService;

    @Autowired
    private CommonService commonService;
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchByKeyWord(@PathVariable String keyword){
        List<Product> productList= productService.findByNameOrDescription(keyword);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllEnable(@RequestParam(defaultValue = "5",name = "limit") int limit,
                                          @RequestParam(defaultValue = "0",name = "page") int page,
                                          @RequestParam(defaultValue = "id",name = "sortBy") String sort,
                                          @RequestParam(defaultValue = "asc",name = "order") String order){
        Pageable pageable=commonService.pagination(order,page,limit,sort);
        Page<Product> productList=productService.getAllEnable(pageable);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


    @GetMapping("{productId}")
    public ResponseEntity<?> productDetailById(@PathVariable Long productId) {
        Product product= productService.findById(productId);
        if(!product.getStatus()){
            throw new RuntimeException();
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> latestProduct(@RequestParam(defaultValue = "5",name = "limit") int limit,
                                           @RequestParam(defaultValue = "0",name = "page") int page,
                                           @RequestParam(defaultValue = "id",name = "sortBy") String sort,
                                           @RequestParam(defaultValue = "asc",name = "order") String order){
        Pageable pageable=commonService.pagination(order,page,limit,sort);
        Page<Product> productList=productService.getAllEnable(pageable);
        List<Product> productListFinal=new ArrayList<>();
        for (Product product :
                productList) {
            if(!commonService.isDateTooOld(product.getCreatedAt())){
                productListFinal.add(product);
            }
        }
        return new ResponseEntity<>(productListFinal,HttpStatus.OK);
    }
}
