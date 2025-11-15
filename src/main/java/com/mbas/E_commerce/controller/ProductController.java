package com.mbas.E_commerce.controller;

import com.mbas.E_commerce.dto.ProductDto;
import com.mbas.E_commerce.entities.Product;
import com.mbas.E_commerce.repository.ProductRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        // List<ProductDto> productDtos = productRepository.findAll().stream()
        //         .map(ProductDto::fromEntity)
        //         .collect(Collectors.toList());
        // return ResponseEntity.ok(productDtos);

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);
    
        Page<ProductDto> productDtoPage = productPage.map(ProductDto::fromEntity);
    
        return ResponseEntity.ok(productDtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ProductDto::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Filter by category-name e.g "/books" or "/electronics"
    @GetMapping("/category-name/{categoryName}")
    public ResponseEntity<?> getProductsByCategoryName(@PathVariable String categoryName) {
        if (!productRepository.existsByCategoryName(categoryName)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(categoryName + " Category not found");
        }

        List<ProductDto> productDtos = productRepository.findByCategoryName(categoryName).stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

     // Search by category name (case-insensitive, partial match)
     @GetMapping("/category-search")
     public ResponseEntity<List<ProductDto>> searchProductsByCategoryName(@RequestParam String categoryName) {
        List<Product> products = productRepository.findByCategoryNameContainingIgnoreCase(categoryName);
        List<ProductDto> productDtos = products.stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(productDtos);
     }
     

}
