package com.shop.onlineshopping.service;

import com.shop.onlineshopping.dao.UserDao;
import com.shop.onlineshopping.domain.Permission;
import com.shop.onlineshopping.domain.User;
import com.shop.onlineshopping.dto.request.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserDao userDao;
    private final PermissionService permissionService;

    @Autowired
    public UserService(UserDao userDao, PermissionService permissionService) {
        this.userDao = userDao;
        this.permissionService = permissionService;
    }

//    public List<User> getAllUsers() {
//        return userDao.getAllUsers();
//    }

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


}
