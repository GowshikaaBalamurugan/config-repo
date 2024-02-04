package com.dkart.productservice.service;

import com.dkart.productservice.entity.Category;
import com.dkart.productservice.exception.CategoryAlreadyExistsException;
import com.dkart.productservice.exception.CategoryNotFoundException;
import com.dkart.productservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        if (categoryRepository.findByCategoryName(category.getCategoryName()).isPresent()){
            throw new CategoryAlreadyExistsException("Category Already Exists for "+category.getCategoryName());
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category checkForCategory(String categoryName) {
        Optional<Category> category=categoryRepository.findByCategoryName(categoryName);
        if(category.isEmpty()){
            throw new CategoryNotFoundException("Category Not Found for categoryName "+categoryName);
        }
        return category.get();    }
}
