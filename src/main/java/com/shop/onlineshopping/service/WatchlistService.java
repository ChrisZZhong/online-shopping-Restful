package com.shop.onlineshopping.service;

import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.dto.response.ProductsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {

    private UserService userService;

    @Autowired
    public WatchlistService(UserService userService) {
        this.userService = userService;
    }

    public List<Product> getWatchlistProducts(Integer userId) {
        return userService.getWatchlistProducts(userId);
    }
}
