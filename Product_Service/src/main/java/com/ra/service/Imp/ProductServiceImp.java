package com.ra.service.Imp;


import com.ra.service.IProductService;
import com.ra.model.entity.Product;
import com.ra.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImp implements IProductService {
    @Autowired
    private ProductRepository productRepository;

//    @Autowired
//    private KafkaTemplate<String,Object> kafkaTemplate;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }



    @Override
    public Product save(Product productRequest) {
        if (productRequest.getId() !=null){
            productRequest.setUpdatedAt(new Date());
        }else {
            productRequest.setSku(UUID.randomUUID().toString());
            productRequest.setCreatedAt(new Date());
        }
//        kafkaTemplate.send("List","product",getAll());
        return productRepository.save(productRequest);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Product Not Found"));
    }

    @Override
    public void deleteById(Long id) {
        Product product=findById(id);
        product.setStatus(!product.getStatus());
        save(product);
    }

    @Override
    public List<Product> findByNameOrDescription(String keyword) {
        return productRepository.findAllByProductNameContainingOrDescriptionContaining(keyword,keyword);
    }

    @Override
    public Page<Product> getAllEnable(Pageable pageable) {
        return productRepository.findAllByStatus(true,pageable);
    }

}
