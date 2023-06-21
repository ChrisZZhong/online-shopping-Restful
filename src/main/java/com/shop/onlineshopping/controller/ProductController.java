package com.shop.onlineshopping.controller;

import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.dto.request.ProductRequest;
import com.shop.onlineshopping.dto.response.ProductResponse;
import com.shop.onlineshopping.dto.response.ProductsResponse;
import com.shop.onlineshopping.dto.response.StatusResponse;
import com.shop.onlineshopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/products/all")
    public ProductsResponse getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ProductsResponse.builder()
                .status("200 OK")
                .message("All products")
                .product(products)
                .build();
    }

    @GetMapping(value = "/products/{id}")
    public ProductResponse updateProduct(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        return ProductResponse.builder()
                .status("200 OK")
                .message("Product with id " + id)
                .product(product)
                .build();
    }

}
