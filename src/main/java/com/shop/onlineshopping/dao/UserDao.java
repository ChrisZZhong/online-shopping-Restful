package com.shop.onlineshopping.dao;

import com.shop.onlineshopping.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDao extends AbstractHibernateDao<User> {

    public UserDao() {
        setClazz(User.class);
    }

    public List<User> getAllUsers() {
        return getAll();
    }

    public Optional<User> loadUserByUsername(String username) {
        return getAll().stream().filter(user -> username.equals(user.getUsername())).findAny();
    }
}
