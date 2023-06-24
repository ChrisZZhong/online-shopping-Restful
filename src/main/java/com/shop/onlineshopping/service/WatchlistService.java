package com.shop.onlineshopping.service;

import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {

    private UserService userService;

    private ProductService productService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> getWatchlistProducts(Integer userId) {
        return userService.getWatchlistProducts(userId);
    }

    @Transactional
    public boolean addProductToWatchlist(Integer userId, Integer id) {
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(id);
        List<Product> watchlist = getWatchlistProducts(userId);
        for (Product p : watchlist) {
            if (p.getProductId().equals(id)) {
                return false;
            }
        }
        watchlist.add(product);
        user.setWatchlist(watchlist);
        userService.updateUser(user);
        return true;
    }

    @Transactional
    public boolean deleteProductFromWatchlist(Integer userId, Integer id) {
        User user = userService.getUserById(userId);
        List<Product> watchlist = getWatchlistProducts(userId);
        Optional<Product> exist =  watchlist.stream().filter(product -> product.getProductId().equals(id)).findFirst();
        if (!exist.isPresent()) {
            return false;
        }
        watchlist.removeIf(product -> product.getProductId().equals(id));
        user.setWatchlist(watchlist);
        userService.updateUser(user);
        return true;
    }
}
