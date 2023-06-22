package com.shop.onlineshopping.dao;

import com.shop.onlineshopping.domain.Item;
import com.shop.onlineshopping.domain.Order;
import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.domain.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Comparator;


@Repository
public class OrderDao extends AbstractHibernateDao<Order>{
    public OrderDao() {
        setClazz(Order.class);
    }

    public void createOrder(Integer userId) {
        Order order = Order.builder()
                .userId(userId)
                .orderStatus("Processing")
                .datePlaced(new Date(System.currentTimeMillis()))
                .build();
        add(order);
    }


    public Integer getCreatedOrderId(Integer userId) {
        return getAll().stream().filter(order -> order.getUserId().equals(userId)).max(Comparator.comparing(Order::getDatePlaced)).get().getOrderId();
    }
}
