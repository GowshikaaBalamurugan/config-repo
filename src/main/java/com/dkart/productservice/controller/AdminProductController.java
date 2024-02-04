package com.dkart.productservice.controller;

import com.dkart.productservice.dto.CustomSuccessResponse;
import com.dkart.productservice.entity.Category;
import com.dkart.productservice.entity.Inventory;
import com.dkart.productservice.entity.Price;
import com.dkart.productservice.entity.Product;
import com.dkart.productservice.exception.CategoryNotFoundException;
import com.dkart.productservice.exception.ProductNotFoundException;
import com.dkart.productservice.service.CategoryService;
import com.dkart.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminProductController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private CustomSuccessResponse customSuccessResponse;
    @Autowired
    public AdminProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @PostMapping("/category")
    public ResponseEntity<Object> addCategory(@Valid @RequestBody Category category) {
            Category savedCategory = categoryService.addCategory(category);
            customSuccessResponse = CustomSuccessResponse.builder()
                .status("SUCCESS")
                .data(category)
                .message("Category "+category.getCategoryName()+" has been added successfully").build();
            return ResponseEntity.status(HttpStatus.CREATED).body(customSuccessResponse);
    }

    @PostMapping("/products/{category}")
    public ResponseEntity<Object> addProduct(@Valid @RequestBody Product product,@PathVariable("category")String categoryName) {
            Product savedProduct = productService.addProduct(product,categoryName);
            customSuccessResponse = CustomSuccessResponse.builder()
                .status("SUCCESS")
                .data(savedProduct)
                .message("Product "+savedProduct.getName()+" has been added successfully").build();
        return ResponseEntity.status(HttpStatus.CREATED).body(customSuccessResponse);
    }

        @PostMapping("/products/bulk/{category}")
    public ResponseEntity<Object> addProducts( @Valid @RequestBody List<Product> products, @PathVariable("category")String categoryName) {
            List<Product> savedProducts = productService.addProducts(products,categoryName);
            String productNames=savedProducts.stream().map(Product::getName).collect(Collectors.joining(","));
            customSuccessResponse = CustomSuccessResponse.builder()
                .status("SUCCESS")
                .data(savedProducts)
                .message("Products ["+productNames+"] have been added successfully").build();
            return ResponseEntity.status(HttpStatus.CREATED).body(customSuccessResponse);
    }

//    @DeleteMapping("/products")
//    public  ResponseEntity<Object> removeProducts(@RequestBody List<String> productNames ) throws BadRequestException {
//            productService.deleteProducts(productNames);
//            customSuccessResponse = CustomSuccessResponse.builder()
//                .status("SUCCESS")
//                .message("Products ["+productNames+"] have been deleted successfully").build();
//            return ResponseEntity.status(HttpStatus.OK).body(customSuccessResponse);
//    }

    @DeleteMapping("/products")
    public  ResponseEntity<Object> removeProduct(@RequestBody String productName ) {
        productService.deleteProduct(productName);
        customSuccessResponse = CustomSuccessResponse.builder()
                .status("SUCCESS")
                .message("Products ["+productName+"] have been deleted successfully").build();
        return ResponseEntity.status(HttpStatus.OK).body(customSuccessResponse);
    }
    @PutMapping("/products/price")
    public ResponseEntity<Object> updatePriceForProducts(@RequestBody Map<String, Price> products){
            List<Product> updatedProducts=productService.updateProductsByPrice(products);
            String productNames=updatedProducts.stream().map(Product::getName).collect(Collectors.joining(","));
            customSuccessResponse = CustomSuccessResponse.builder()
                .status("SUCCESS")
                .data(updatedProducts)
                .message("Products ["+productNames+"] have been updated successfully").build();
            return ResponseEntity.status(HttpStatus.OK).body(customSuccessResponse);
    }
    @PutMapping("/products/price/{productName}")
    public ResponseEntity<Object> updateProductByPrice( @PathVariable String productName, @RequestBody Price price){
        Product updatedProduct=productService.updateProductByPrice(productName,price);
        customSuccessResponse = CustomSuccessResponse.builder()
                .status("SUCCESS")
                .data(updatedProduct)
                .message("Product ["+productName+"] has been updated successfully").build();
        return ResponseEntity.status(HttpStatus.OK).body(customSuccessResponse);
    }
    @PutMapping("/products/inventory/{productName}")
    public ResponseEntity<Object> updateProductByInventory( @PathVariable String productName, @RequestBody Inventory inventory){
        Product updatedProduct=productService.updateProductByInventory(productName,inventory);
        customSuccessResponse = CustomSuccessResponse.builder()
                .status("SUCCESS")
                .data(updatedProduct)
                .message("Product ["+productName+"] has been updated successfully").build();
        return ResponseEntity.status(HttpStatus.OK).body(customSuccessResponse);
    }

}
