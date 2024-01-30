package com.dkart.productservice.service;

import com.dkart.productservice.entity.Category;
import com.dkart.productservice.entity.Price;
import com.dkart.productservice.entity.Product;
import com.dkart.productservice.exception.ProductNotFoundException;
import com.dkart.productservice.repository.ProductRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product addProduct(Product product,String categoryName) {
        Category category=categoryService.checkForCategory(categoryName);
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public List<Product> addProducts(List<Product> products, String categoryName) {
        Category category=categoryService.checkForCategory(categoryName);
        List<Product> productSaved=new ArrayList<>();
         products.forEach(product->{
                    product.setCategory(category);
                    productSaved.add(productRepository.save(product));

                });
         return productSaved;
    }



    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProducts(List<String> products) throws BadRequestException {
        if (products.isEmpty()){
            throw new BadRequestException("Provide Product names to remove");
        }
         productRepository.deleteAllByNameIn(products);
    }

    @Override
    public List<Product> updatePrice(Map<String, Price> products) {
        List<Product> updatedProducts=new ArrayList<>();
        products.forEach((key, value) -> {
              Optional<Product> product=productRepository.findProductByName(key);
              if (product.isEmpty()){
                  throw new ProductNotFoundException("Product Not found");
              }
              product.get().setPrice(value);
              updatedProducts.add(productRepository.save(product.get()));
        });
        return updatedProducts;
    }
}
