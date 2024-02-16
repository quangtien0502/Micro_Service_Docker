package com.ra.service.Imp;

import com.ra.service.ICategoryService;
import com.ra.model.entity.Category;
import com.ra.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

//    @Autowired
//    private KafkaTemplate<String,Object> kafkaTemplate;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> getAllEnable(Pageable pageable) {
        return categoryRepository.findCategoriesByStatus(true,pageable);
    }

    @Override
    public Boolean checkDuplicateName(String name) {
        return categoryRepository.existsCategoryByCategoryName(name);
    }

    @Override
    public Category save(Category categoryRequest){
        if(categoryRequest.getId()==null && checkDuplicateName(categoryRequest.getCategoryName())){
            throw new RuntimeException("Category Name already Exist");
        }
//        kafkaTemplate.send("list","category",getAll());
        return categoryRepository.save(categoryRequest);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category Not Found"));
    }

    @Override
    public void deleteById(Long id) {
        Category category=findById(id);
        category.setStatus(!category.getStatus());
        save(category);
    }


}
