package com.shop.onlineshopping.controller;

import com.shop.onlineshopping.domain.Item;
import com.shop.onlineshopping.domain.Order;
import com.shop.onlineshopping.domain.User;
import com.shop.onlineshopping.dto.request.OrderRequest;
import com.shop.onlineshopping.dto.response.*;
import com.shop.onlineshopping.security.AuthUserDetail;
import com.shop.onlineshopping.service.OrderService;
import com.shop.onlineshopping.service.ProductService;
import com.shop.onlineshopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
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
    // place new order
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

    @GetMapping("/orders/all")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ResponseEntity<OrdersResponse> getAllOrders(@AuthenticationPrincipal AuthUserDetail authUserDetail) {
        List<Order> order;
        if (authUserDetail.hasAuthority("user")) {
            User user = userService.getUserByUsername(authUserDetail.getUsername()).get();
            order = orderService.getOrdersByUserId(user.getUserId());
        } else {
            order = orderService.getAllOrders();
        }
        for (Order o : order) {
            o.setItems(null);
        }
        return ResponseEntity.ok(
                OrdersResponse.builder()
                .status("success")
                .message("Orders retrieved successfully")
                .order(order)
                .build()
        );

    }

    @GetMapping("/orders/{Id}")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ResponseEntity<OrderResponse> getOrderByOrderId(@AuthenticationPrincipal AuthUserDetail authUserDetail, @PathVariable Integer Id) {
        Order order = orderService.loadOrder(orderService.getOrderByOrderId(Id));
        if (authUserDetail.hasAuthority("user")) {
            User user = userService.getUserByUsername(authUserDetail.getUsername()).get();
            // check order belongs to user?
            if (orderService.getOrdersByUserId(user.getUserId()).stream().noneMatch(o -> o.getOrderId().equals(Id))) {
                return ResponseEntity.ok(OrderResponse.builder()
                        .status("failed")
                        .message("can't access other user's order")
                        .build());
            }
            for (Item i : order.getItems()) {
                i.setOrderId(null);
                i.setProductId(null);
                i.setWholesalePrice(null);
                i.getProduct().setQuantity(null);
                i.getProduct().setWholesalePrice(null);
            }
        }
        order.setUserId(null);
        return ResponseEntity.ok(OrderResponse.builder()
                .status("success")
                .message("Orders retrieved successfully")
                .order(order)
                .build());

    }

    @PatchMapping("/orders/{Id}/cancel")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ResponseEntity<StatusResponse> cancelOrder(@AuthenticationPrincipal AuthUserDetail authUserDetail, @PathVariable Integer Id) {
        if (authUserDetail.hasAuthority("user")) {
            User user = userService.getUserByUsername(authUserDetail.getUsername()).get();
            // check order belongs to user?
            if (orderService.getOrdersByUserId(user.getUserId()).stream().noneMatch(order -> order.getOrderId().equals(Id))) {
                return ResponseEntity.ok(StatusResponse.builder()
                        .status("failed")
                        .message("can't access other user's order")
                        .build());
            }
        }
        Order order = orderService.getOrderByOrderId(Id);
        if (orderService.cancelOrder(order)) {
            return ResponseEntity.ok(StatusResponse.builder()
                    .status("success")
                    .message("Order cancelled successfully")
                    .build());
        } else {
            return ResponseEntity.ok(StatusResponse.builder()
                    .status("failed")
                    .message("Order cancellation failed")
                    .build());
        }
    }

    @PatchMapping("/orders/{Id}/complete")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<StatusResponse> completeOrder(@AuthenticationPrincipal AuthUserDetail authUserDetail, @PathVariable Integer Id) {
//        if (authUserDetail.hasAuthority("user")) {
//            User user = userService.getUserByUsername(authUserDetail.getUsername()).get();
//            // check order belongs to user?
//            if (orderService.getOrdersByUserId(user.getUserId()).stream().noneMatch(order -> order.getOrderId().equals(Id))) {
//                return ResponseEntity.ok(StatusResponse.builder()
//                        .status("failed")
//                        .message("can't access other user's order")
//                        .build());
//            }
//        }
        Order order = orderService.getOrderByOrderId(Id);
        if (orderService.completeOrder(order)) {
            return ResponseEntity.ok(StatusResponse.builder()
                    .status("success")
                    .message("Order completed successfully")
                    .build());
        } else {
            return ResponseEntity.ok(StatusResponse.builder()
                    .status("failed")
                    .message("Order completion failed")
                    .build());
        }
    }




}
