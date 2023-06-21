package com.shop.onlineshopping;

import com.shop.onlineshopping.domain.User;
import com.shop.onlineshopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.util.List;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class OnlineShoppingApplication {
//    private static UserService;
//
//    @Autowired
//    public OnlineShoppingApplication(UserService userService) {
//        this.userService = userService;
//    }

    public static void main(String[] args) {
        SpringApplication.run(OnlineShoppingApplication.class, args);
    }

}
