package com.shop.onlineshopping.controller;

import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.dto.request.ProductRequest;
import com.shop.onlineshopping.dto.response.ProductResponse;
import com.shop.onlineshopping.dto.response.ProductsResponse;
import com.shop.onlineshopping.dto.response.StatusResponse;
import com.shop.onlineshopping.security.AuthUserDetail;
import com.shop.onlineshopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    // User : The user is able to view all the products. An out-of-stock product should NOT be shown to the user.
    // Admin : Listing information, the current products that are listed to sell.
    public ProductsResponse getAllProducts(@AuthenticationPrincipal AuthUserDetail authUserDetail) {
        List<Product> products = new ArrayList<>();
        if (authUserDetail.hasAuthority("user")) {
            products = productService.getOnsaleProducts();
        } else if (authUserDetail.hasAuthority("admin")){
            products = productService.getAllProducts();
        }
        return ProductsResponse.builder()
                .status("200 OK")
                .message("All products")
                .products(products)
                .build();
    }

    @GetMapping(value = "/products/{id}")
    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    // TODO separate admin and user
    public ProductResponse updateProduct(@PathVariable Integer id, @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        Product product = productService.getProductById(id);
        if (authUserDetail.hasAuthority("user")) {
            product.setQuantity(null);
            product.setWholesalePrice(null);
        }
        return ProductResponse.builder()
                .status("200 OK")
                .message("Product with id " + id)
                .product(product)
                .build();
    }

}
