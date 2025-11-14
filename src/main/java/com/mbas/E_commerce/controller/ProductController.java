package com.mbas.E_commerce.controller;

import com.mbas.E_commerce.dto.ProductDto;
import com.mbas.E_commerce.entities.Product;
import com.mbas.E_commerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCategory().getName()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCategory().getName()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Filter by category name
    @GetMapping("/category-name/{categoryName}")
    public List<ProductDto> getProductsByCategoryName(@PathVariable String categoryName) {
        List<Product> products = productRepository.findByCategoryName(categoryName);

        return products.stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCategory().getName()
                ))
                .collect(Collectors.toList());
    }

}
