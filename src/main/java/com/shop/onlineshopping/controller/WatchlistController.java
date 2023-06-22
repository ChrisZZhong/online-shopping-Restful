package com.shop.onlineshopping.controller;

import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.domain.User;
import com.shop.onlineshopping.dto.response.ProductsResponse;
import com.shop.onlineshopping.security.AuthUserDetail;
import com.shop.onlineshopping.service.UserService;
import com.shop.onlineshopping.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
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
        for (Product product : products) {
            product.setWholesalePrice(null);
        }
        return ProductsResponse.builder()
                .status("200 OK")
                .message("Get watchlist products successfully")
                .products(products)
                .build();
    }

}
