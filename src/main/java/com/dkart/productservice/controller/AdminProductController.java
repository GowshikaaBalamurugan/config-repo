package com.dkart.productservice.controller;

import com.dkart.productservice.entity.Category;
import com.dkart.productservice.entity.Price;
import com.dkart.productservice.entity.Product;
import com.dkart.productservice.exception.CategoryNotFoundException;
import com.dkart.productservice.exception.ProductNotFoundException;
import com.dkart.productservice.service.CategoryService;
import com.dkart.productservice.service.ProductService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminProductController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public AdminProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @PostMapping("/category")
    public ResponseEntity<Object> addCategory(@RequestBody Category category) {
        try {
            Category savedCategory = categoryService.addCategory(category);
            //if (savedCategory != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
            //}
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(e.getMessage());
        }

    }
    @PostMapping("/products/{category}")
    public ResponseEntity<Object> addProduct(@RequestBody Product product, @PathVariable("category")String categoryName) {
        try {
//            Category category=new Category();
//            category=product.setCategory(category.setCategoryName(categoryName));
            Product savedProduct = productService.addProduct(product,categoryName);
            //if (savedCategory != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
            //}
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/products/bulk/{category}")
    public ResponseEntity<Object> addProducts(@RequestBody List<Product> products, @PathVariable("category")String categoryName) {
        try {
//            Category category=new Category();
//            category=product.setCategory(category.setCategoryName(categoryName));
            List<Product> savedProducts = productService.addProducts(products,categoryName);
            //if (savedCategory != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProducts);
            //}
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/products")
    public  ResponseEntity<Object> removeProducts(@RequestBody List<String> productNames ){
        try{
            productService.deleteProducts(productNames);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Products Deleted");
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/products/price")
    public ResponseEntity<Object> updatePriceForProducts(@RequestBody Map<String, Price> products){
        try{
            List<Product> updatedProducts=productService.updatePrice(products);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProducts);
        }catch(ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
