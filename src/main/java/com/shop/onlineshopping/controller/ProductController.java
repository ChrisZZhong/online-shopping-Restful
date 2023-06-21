package com.shop.onlineshopping.controller;

import com.shop.onlineshopping.dto.request.ProductRequest;
import com.shop.onlineshopping.dto.response.StatusResponse;
import com.shop.onlineshopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
//        try {
        productService.addProduct(productRequest);
        return StatusResponse.builder()
                .status("200 OK")
                .message("Product added successfully")
                .build();
//        } catch (Exception e) {
//            return StatusResponse.builder()
//                    .status("404")
//                    .message(e.getMessage())
//                    .build();
//        }
    }

}
