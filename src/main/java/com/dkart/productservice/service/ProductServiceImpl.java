package com.dkart.productservice.service;

import com.dkart.productservice.entity.Category;
import com.dkart.productservice.entity.Product;
import com.dkart.productservice.exception.CategoryNotFoundException;
import com.dkart.productservice.repository.CategoryRepository;
import com.dkart.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product addProduct(Product product,String categoryName) {
        Optional<Category> category=categoryRepository.findByCategoryName(categoryName);
        if(category.isEmpty()){
            throw new CategoryNotFoundException("Category Not Found");
        }
        product.setCategory(category.get());
        return productRepository.save(product);
    }
}
