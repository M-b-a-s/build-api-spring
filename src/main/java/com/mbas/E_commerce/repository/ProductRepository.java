package com.mbas.E_commerce.repository;

import com.mbas.E_commerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String name);
    boolean existsByCategoryName(String name);
    List<Product> findByCategoryNameContainingIgnoreCase(String categoryName);
}
