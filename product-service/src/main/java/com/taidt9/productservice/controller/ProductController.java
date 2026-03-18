package com.taidt9.productservice.controller;

import com.taidt9.productservice.api.ApiResponse;
import com.taidt9.productservice.dto.ProductRequest;
import com.taidt9.productservice.dto.ProductResponse;
import com.taidt9.productservice.entity.Product;
import com.taidt9.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ApiResponse<ProductResponse> create(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = service.create(productRequest);
        return ApiResponse.<ProductResponse>builder().success(true).data(productResponse).timestamp(LocalDateTime.now()).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> get(@PathVariable Long id, @RequestHeader("X-User-Name") String username) {
        Product product = service.getById(id);
        ProductResponse productResponse = ProductResponse.builder().id(product.getId()).name(product.getName()).price(product.getPrice()).build();
        return ApiResponse.<ProductResponse>builder().success(true).data(productResponse).timestamp(LocalDateTime.now()).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        Product product = service.update(id, productRequest);
        ProductResponse productResponse = ProductResponse.builder().id(product.getId()).name(product.getName()).price(product.getPrice()).build();
        return ApiResponse.<ProductResponse>builder().success(true).data(productResponse).timestamp(LocalDateTime.now()).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.<Void>builder().success(true).build();
    }
}
