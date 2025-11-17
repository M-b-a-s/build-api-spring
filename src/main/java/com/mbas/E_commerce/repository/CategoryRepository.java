package com.mbas.E_commerce.repository;

import com.mbas.E_commerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Byte> {
    Optional<Category> findByName(String name);
}
