package com.dkart.productservice.controller;

import com.dkart.productservice.entity.Product;
import com.dkart.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class ProductController {

    private final ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<Object> getAllProducts(){
        List<Product> products=productService.getAllProducts();
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No product Found..");
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);

    }
}
