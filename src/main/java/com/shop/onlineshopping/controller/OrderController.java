package com.shop.onlineshopping.controller;

import com.shop.onlineshopping.domain.Item;
import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.domain.User;
import com.shop.onlineshopping.dto.request.OrderRequest;
import com.shop.onlineshopping.dto.response.StatusResponse;
import com.shop.onlineshopping.security.AuthUserDetail;
import com.shop.onlineshopping.service.OrderService;
import com.shop.onlineshopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;
    private UserService userService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/orders")
    @PreAuthorize("hasAuthority('user')")
    public StatusResponse placeOrder(@RequestBody OrderRequest orderRequest, @AuthenticationPrincipal AuthUserDetail authUserDetail) {
        User user = userService.getUserByUsername(authUserDetail.getUsername()).get();
        List<Item> orderItems = orderRequest.getItems();
        if (orderService.placeOrder(orderItems, user.getUserId())) {
            return StatusResponse.builder()
                    .status("success")
                    .message("Order placed successfully")
                    .build();
        } else {
            return StatusResponse.builder()
                    .status("failed")
                    .message("Order failed")
                    .build();
        }
    }

}
