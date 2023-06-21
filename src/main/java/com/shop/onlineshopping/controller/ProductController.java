package com.shop.onlineshopping.controller;

import com.shop.onlineshopping.dto.request.ProductRequest;
import com.shop.onlineshopping.dto.response.StatusResponse;
import com.shop.onlineshopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/products")
    @PreAuthorize("hasAuthority('admin')")
    public StatusResponse addProduct(@RequestBody ProductRequest productRequest) {
        productService.addProduct(productRequest);
        return StatusResponse.builder()
                .status("200 OK")
                .message("Product added successfully")
                .build();
    }

    @PatchMapping(value = "/products/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public StatusResponse updateProduct(@RequestBody ProductRequest productRequest, @PathVariable Integer id) {
        productService.updateProduct(productRequest, id);
        return StatusResponse.builder()
                .status("200 OK")
                .message("Product updated successfully")
                .build();
    }

}
