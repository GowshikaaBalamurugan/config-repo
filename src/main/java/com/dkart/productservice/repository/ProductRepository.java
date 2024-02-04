package com.dkart.productservice.repository;

import com.dkart.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    void deleteAllByNameIn(List<String> productNames);
    Optional<Product> findProductByName(String productName);

}
