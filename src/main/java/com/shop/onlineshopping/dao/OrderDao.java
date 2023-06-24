package com.shop.onlineshopping.dao;

import com.shop.onlineshopping.domain.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Repository
@Transactional
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

    public List<Order> getOrdersByUserId(Integer userId) {
        return getAll().stream().filter(order -> order.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public List<Order> getAllOrders() {
        return getAll();
    }

    public Order getOrdersByOrderId(Integer id) {
        return findById(id);
    }

    public void updateOrder(Order order) {
        getCurrentSession().update(order);
    }

}
