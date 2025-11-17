package com.mbas.E_commerce.controller;

import com.mbas.E_commerce.dto.ProductDto;
import com.mbas.E_commerce.entities.Category;
import com.mbas.E_commerce.entities.Product;
import com.mbas.E_commerce.repository.CategoryRepository;
import com.mbas.E_commerce.repository.ProductRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@Data
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

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

     // create new product endpoint
     @PostMapping
     public ResponseEntity<ProductDto> createProduct(
        @Valid @RequestBody ProductDto productDto,
        UriComponentsBuilder uriBuilder) {

        // Find category by name
         Category category = categoryRepository.findByName(productDto.getCategoryName())
                 .orElseGet(() -> {
                     Category newCat = new Category();
                     newCat.setName(productDto.getCategoryName());
                     return categoryRepository.save(newCat);
                 });

         Product product = Product.builder()
                 .name(productDto.getName())
                 .description(productDto.getDescription())
                 .price(productDto.getPrice())
                 .category(category)
                 .build();

         Product saved = productRepository.save(product);
         URI location = uriBuilder.path("/products/{id}").buildAndExpand(saved.getId()).toUri();
         return ResponseEntity.created(location).body(ProductDto.fromEntity(saved));
     }

}
