package com.dkart.productservice.service;

import com.dkart.productservice.entity.Price;
import com.dkart.productservice.entity.Product;
import org.apache.coyote.BadRequestException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductService {

    Product addProduct(Product product,String categoryName);
    List<Product> addProducts(List<Product> products, String categoryName);

    List<Product> getAllProducts();

    void deleteProducts(List<String> products) throws BadRequestException;

    List<Product> updatePrice(Map<String, Price> products);
}
