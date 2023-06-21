package com.shop.onlineshopping.controller;

import com.shop.onlineshopping.domain.User;
import com.shop.onlineshopping.dto.request.SignUpRequest;
import com.shop.onlineshopping.dto.response.LoginResponse;
import com.shop.onlineshopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
