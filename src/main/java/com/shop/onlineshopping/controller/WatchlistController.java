package com.shop.onlineshopping.controller;

import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.domain.User;
import com.shop.onlineshopping.dto.response.ProductRespons.ProductsResponse;
import com.shop.onlineshopping.dto.response.StatusResponse;
import com.shop.onlineshopping.security.AuthUserDetail;
import com.shop.onlineshopping.service.UserService;
import com.shop.onlineshopping.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/watchlist/products/all")
    public ProductsResponse getWatchlistProducts(@AuthenticationPrincipal AuthUserDetail authUserDetail) {
        User user = userService.getUserByUsername(authUserDetail.getUsername()).get();
        List<Product> products = watchlistService.getWatchlistProducts(user.getUserId());
        products.removeIf(product -> product.getQuantity() == 0);
        for (Product product : products) {
            product.setWholesalePrice(null);
        }
        return ProductsResponse.builder()
                .status("200 OK")
                .message("Get watchlist products successfully")
                .products(products)
                .build();
    }

    @PostMapping("/watchlist/product/{Id}")
    @PreAuthorize("hasAuthority('user')")
    public StatusResponse addProductToWatchlist(@AuthenticationPrincipal AuthUserDetail authUserDetail, @PathVariable Integer Id) {
        User user = userService.getUserByUsername(authUserDetail.getUsername()).get();
        if (watchlistService.addProductToWatchlist(user.getUserId(), Id)) {
            return StatusResponse.builder()
                    .status("200 OK")
                    .message("Add product to watchlist successfully")
                    .build();
        } else {
            return StatusResponse.builder()
                    .status("400 BAD REQUEST")
                    .message("Product already in watchlist")
                    .build();
        }
    }

    @DeleteMapping("/watchlist/product/{Id}")
    @PreAuthorize("hasAuthority('user')")
    public StatusResponse deleteProductFromWatchlist(@AuthenticationPrincipal AuthUserDetail authUserDetail, @PathVariable Integer Id) {
        User user = userService.getUserByUsername(authUserDetail.getUsername()).get();
        if (watchlistService.deleteProductFromWatchlist(user.getUserId(), Id)) {
            return StatusResponse.builder()
                    .status("200 OK")
                    .message("Delete product from watchlist successfully")
                    .build();
        } else {
            return StatusResponse.builder()
                    .status("400 BAD REQUEST")
                    .message("Product not in watchlist")
                    .build();
        }
    }



}
