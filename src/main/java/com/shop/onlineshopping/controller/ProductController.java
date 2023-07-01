package com.shop.onlineshopping.controller;

import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.domain.User;
import com.shop.onlineshopping.dto.response.ProductRespons.*;
import com.shop.onlineshopping.dto.response.ProductRespons.domain.FrequentProduct;
import com.shop.onlineshopping.dto.response.ProductRespons.domain.PopularProduct;
import com.shop.onlineshopping.dto.response.ProductRespons.domain.ProfitProduct;
import com.shop.onlineshopping.dto.request.ProductRequest;
import com.shop.onlineshopping.dto.response.*;
import com.shop.onlineshopping.dto.response.ProductRespons.domain.RecentProduct;
import com.shop.onlineshopping.security.AuthUserDetail;
import com.shop.onlineshopping.service.ProductService;
import com.shop.onlineshopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    private final UserService userService;

    @Autowired
    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
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
        productService.updateProductById(productRequest, id);
        return StatusResponse.builder()
                .status("200 OK")
                .message("Product updated successfully")
                .build();
    }

    @GetMapping(value = "/products/all")
    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    // User : The user is able to view all the products. An out-of-stock product should NOT be shown to the user.
    // Admin : Listing information, the current products that are listed to sell.
    public ResponseEntity<ProductsResponse> getAllProducts(@AuthenticationPrincipal AuthUserDetail authUserDetail) {
        List<Product> products = new ArrayList<>();
        if (authUserDetail.hasAuthority("user")) {
            products = productService.getOnsaleProducts();
            products.removeIf(product -> product.getQuantity() == 0);
            for (Product product : products) {
                product.setWholesalePrice(null);
                product.setQuantity(null);
            }
        } else if (authUserDetail.hasAuthority("admin")){
            products = productService.getAllProducts();
        }
        return ResponseEntity.ok().body(ProductsResponse.builder()
                .status("200 OK")
                .message("All products")
                .products(products)
                .build());
    }

    @GetMapping(value = "/products/{id}")
    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Integer id, @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        Product product = productService.getProductById(id);
        if (authUserDetail.hasAuthority("user")) {
            if (product.getQuantity() == 0) {
                return ResponseEntity.badRequest().body(ProductResponse.builder()
                        .status("failed")
                        .message("product out of stock")
                        .product(null)
                        .build());
            }
            product.setQuantity(null);
            product.setWholesalePrice(null);
        }
        return ResponseEntity.ok().body(
                ProductResponse.builder()
                .status("200 OK")
                .message("Product with id " + id)
                .product(product)
                .build());
    }

    // below is derived attributes for summary table
    @GetMapping("/products/popular/{Id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<PopularProductsResponse> getTopPopularProducts(@PathVariable Integer Id) {
        List<PopularProduct> popularProducts = productService.getTopPopularProducts(Id);
        return ResponseEntity.ok(PopularProductsResponse.builder()
                .status("success")
                .message("Top " + (Id == 0 ? "all" : Id) + " popular products retrieved successfully")
                .popularProducts(popularProducts)
                .build());
    }

    @GetMapping("/products/profit/{Id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ProfitProductsResponse> getTopProfitProducts(@PathVariable Integer Id) {
        List<ProfitProduct> popularProducts = productService.getTopProfitProducts(Id);
        return ResponseEntity.ok(ProfitProductsResponse.builder()
                .status("success")
                .message("Top " + (Id == 0 ? "all" : Id) + " profitable products retrieved successfully")
                .profitProducts(popularProducts)
                .build());
    }

    @GetMapping("/products/frequent/{Id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<FrequentProductsResponse> getTopFrequentProducts(@PathVariable Integer Id, @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        User user = userService.getUserByUsername(authUserDetail.getUsername()).get();
        List<FrequentProduct> popularProducts = productService.getTopFrequentProductsByUserId(Id, user.getUserId());
        return ResponseEntity.ok(FrequentProductsResponse.builder()
                .status("success")
                .message("Top " + (Id == 0 ? "all" : Id) + " frequent products retrieved successfully")
                .frequentProducts(popularProducts)
                .build());
    }

    @GetMapping("/products/recent/{Id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<RecentProductsResponse> getTopRecentProducts(@PathVariable Integer Id, @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        User user = userService.getUserByUsername(authUserDetail.getUsername()).get();
        List<RecentProduct> popularProducts = productService.getTopRecentProductsByUserId(Id, user.getUserId());
        return ResponseEntity.ok(RecentProductsResponse.builder()
                .status("success")
                .message("Top " + (Id == 0 ? "all" : Id) + " recent products retrieved successfully")
                .recentProducts(popularProducts)
                .build());
    }


}
