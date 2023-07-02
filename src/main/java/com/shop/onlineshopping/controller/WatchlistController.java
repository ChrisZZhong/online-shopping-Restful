package com.shop.onlineshopping.controller;

import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.domain.User;
import com.shop.onlineshopping.dto.response.ProductRespons.ProductsResponse;
import com.shop.onlineshopping.dto.response.StatusResponse;
import com.shop.onlineshopping.security.AuthUserDetail;
import com.shop.onlineshopping.service.UserService;
import com.shop.onlineshopping.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class WatchlistController {

    private WatchlistService watchlistService;

    private UserService userService;

    @Autowired
    public WatchlistController(WatchlistService watchlistService, UserService userService) {
        this.watchlistService = watchlistService;
        this.userService = userService;
    }

    @GetMapping("/watchlist/product/all")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<ProductsResponse> getWatchlistProducts(@AuthenticationPrincipal AuthUserDetail authUserDetail) {
        User user = userService.getUserByUsername(authUserDetail.getUsername()).get();
        List<Product> products = watchlistService.getWatchlistProducts(user.getUserId());
        products.removeIf(product -> product.getQuantity() == 0);
        for (Product product : products) {
            product.setWholesalePrice(null);
            product.setQuantity(null);
        }
        return ResponseEntity.ok(ProductsResponse.builder()
                .status("success")
                .message("Get watchlist products successfully")
                .products(products)
                .build());
    }

    @PostMapping("/watchlist/product/{Id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<StatusResponse> addProductToWatchlist(@AuthenticationPrincipal AuthUserDetail authUserDetail, @PathVariable Integer Id) {
        User user = userService.getUserByUsername(authUserDetail.getUsername()).get();
        if (watchlistService.addProductToWatchlist(user.getUserId(), Id)) {
            return ResponseEntity.ok(
                    StatusResponse.builder()
                    .status("success")
                    .message("Add product to watchlist successfully")
                    .build()
            );
        } else {
            return ResponseEntity.ok(
                    StatusResponse.builder()
                    .status("failed")
                    .message("Product already in watchlist")
                    .build()
            );
        }
    }

    @DeleteMapping("/watchlist/product/{Id}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<StatusResponse> deleteProductFromWatchlist(@AuthenticationPrincipal AuthUserDetail authUserDetail, @PathVariable Integer Id) {
        User user = userService.getUserByUsername(authUserDetail.getUsername()).get();
        if (watchlistService.deleteProductFromWatchlist(user.getUserId(), Id)) {
            return ResponseEntity.ok(
                    StatusResponse.builder()
                    .status("success")
                    .message("Delete product from watchlist successfully")
                    .build()
            );
        } else {
            return ResponseEntity.ok(
                    StatusResponse.builder()
                    .status("failed")
                    .message("Product not in watchlist")
                    .build()
            );
        }
    }



}
