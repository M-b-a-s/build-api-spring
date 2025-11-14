package com.mbas.E_commerce.controller;

import com.mbas.E_commerce.dto.ProductDto;
import com.mbas.E_commerce.entities.Product;
import com.mbas.E_commerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        var product = productRepository.findById(id).orElse(null);

        return ResponseEntity.ok(product);
    }

//    @GetMapping("/category/{categoryId}")
//    public List<Product> getProductsByCategory(@PathVariable Byte categoryId) {
//        return productRepository.findByCategoryId(categoryId);
//    }
}
