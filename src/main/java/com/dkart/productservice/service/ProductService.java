package com.dkart.productservice.service;

import com.dkart.productservice.entity.Inventory;
import com.dkart.productservice.entity.Price;
import com.dkart.productservice.entity.Product;
import org.apache.coyote.BadRequestException;
import org.springframework.cache.annotation.CachePut;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductService {

    Product addProduct(Product product, String categoryName);

    List<Product> addProducts(List<Product> products, String categoryName);

    List<Product> getAllProducts();


    List<Product> getAllProductsByCategoryAndPrice(String categoryName, BigDecimal minAmount, BigDecimal maxAmount, String currency);

    List<Product> getAllProductsByCategory(String categoryName);

    // List<Product> getAllProductsByCategory(String categoryName, BigDecimal amount);
    //
    void deleteProduct(String productName);

    void deleteProducts(List<String> products) throws BadRequestException;



    @CachePut(value="productsCache",key = "#products") //updating cache
    List<Product> updateProductsByPrice(Map<String, Price> products);

    Product updateProductByInventory(String productName, Inventory inventory) ;

    @CachePut(value="productsCache",key = "#productName") //updating cache
    Product updateProductByPrice(String productName, Price price);
}
