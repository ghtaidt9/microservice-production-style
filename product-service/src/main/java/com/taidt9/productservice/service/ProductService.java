package com.taidt9.productservice.service;

import com.taidt9.productservice.dto.ProductRequest;
import com.taidt9.productservice.dto.ProductResponse;
import com.taidt9.productservice.entity.Product;
import com.taidt9.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Cacheable(value = "products", key = "#id")
    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @CacheEvict(value = "products", key = "#result.id", condition = "#result != null")
    public ProductResponse create(ProductRequest productRequest) {

        Product product = Product.builder().name(productRequest.getName()).price(productRequest.getPrice()).build();
        repository.save(product);
        return ProductResponse.builder().id(product.getId()).name(product.getName()).price(product.getPrice()).build();
    }

    @CacheEvict(value = "products", key = "#id")
    public Product update(Long id, ProductRequest productRequest) {
        Product product = Product.builder().id(id).name(productRequest.getName()).price(productRequest.getPrice()).build();
        return repository.save(product);
    }

    @CacheEvict(value = "products", key = "#id")
    public void delete(Long id) {
        repository.deleteById(id);
    }
}