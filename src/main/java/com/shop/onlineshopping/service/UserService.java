package com.shop.onlineshopping.service;

import com.shop.onlineshopping.dao.UserDao;
import com.shop.onlineshopping.domain.Permission;
import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.domain.User;
import com.shop.onlineshopping.dto.request.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;
    private final PermissionService permissionService;

    @Autowired
    public UserService(UserDao userDao, PermissionService permissionService) {
        this.userDao = userDao;
        this.permissionService = permissionService;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword())
                .email(signUpRequest.getEmail())
                .role(0)
                .build();
        userDao.signUp(user);
        Integer userId = userDao.getUserIdByUsername(signUpRequest.getUsername());
        Permission permission = Permission.builder()
                .value("user")
                .userId(userId)
                .build();
        permissionService.addPermission(permission);

    }

    public Optional<User> getUserByUsername(String username) {
        return getAllUsers().stream()
                .filter(user -> user.getUsername().equals(username)).findFirst();
    }

    public Optional<User> getUserByEmail(String email) {
        return getAllUsers().stream()
                .filter(user -> user.getEmail().equals(email)).findFirst();
    }

    public List<Product> getWatchlistProducts(Integer userId) {
        return userDao.getWatchlistProducts(userId);
    }

    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }
}
