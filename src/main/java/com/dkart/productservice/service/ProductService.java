package com.dkart.productservice.service;

import com.dkart.productservice.entity.Product;

public interface ProductService {

    Product addProduct(Product product,String categoryName);
}
