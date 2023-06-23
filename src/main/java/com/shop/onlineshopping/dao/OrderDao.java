package com.shop.onlineshopping.dao;

import com.shop.onlineshopping.domain.Item;
import com.shop.onlineshopping.domain.Order;
import com.shop.onlineshopping.dto.PopularProduct;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
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

    public List<PopularProduct> getTopPopularProducts(Integer limit) {
        /*
        select order_item.product_id, product.name, product.description, sum(order_item.quantity) as sales
        from order_item, orders, product
        where order_item.order_id = orders.order_id
        and product.product_id = order_item.product_id
        and orders.order_status = "Completed"
        group by product_id
        order by sales desc
        limit 3;
         */
        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<PopularProduct> criteriaQuery = criteriaBuilder.createQuery(PopularProduct.class);
        Root<Item> root = criteriaQuery.from(Item.class);
        root.join("order", JoinType.INNER);
        root.join("product", JoinType.INNER);
        criteriaQuery.multiselect(
                root.get("product").get("productId"),
                root.get("product").get("name"),
                root.get("product").get("description"),
                criteriaBuilder.sum(root.get("quantity")).alias("sales")
        );
        criteriaQuery.where(criteriaBuilder.equal(root.get("order").get("orderStatus"), "Completed"));
        criteriaQuery.groupBy(root.get("product").get("productId"));
        criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(root.get("quantity"))));
        return session.createQuery(criteriaQuery).setMaxResults(limit).getResultList();
    }
}
