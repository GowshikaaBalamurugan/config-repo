package com.dkart.productservice.controller;

import com.dkart.productservice.dto.CustomSuccessResponse;
import com.dkart.productservice.entity.Product;
import com.dkart.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class ProductController {

    private final ProductService productService;

    private CustomSuccessResponse customSuccessResponse;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{categoryName}/{minAmount}/{maxAmount}/{currency}")
    public ResponseEntity<Object> getAllProducts(@PathVariable String categoryName,@PathVariable BigDecimal minAmount,@PathVariable BigDecimal maxAmount,@PathVariable String currency){
        List<Product> products=productService.getAllProductsByCategoryAndPrice(categoryName,minAmount,maxAmount,currency);
        customSuccessResponse = CustomSuccessResponse.builder()
                .status("SUCCESS")
                .data(products)
                .message("Products have been retrieved successfully").build();
        return ResponseEntity.status(HttpStatus.OK).body(customSuccessResponse);

    }
}
