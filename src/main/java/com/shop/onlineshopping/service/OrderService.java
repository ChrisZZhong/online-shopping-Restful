package com.shop.onlineshopping.service;

import com.shop.onlineshopping.dao.ItemDao;
import com.shop.onlineshopping.dao.OrderDao;
import com.shop.onlineshopping.domain.Item;
import com.shop.onlineshopping.domain.Order;
import com.shop.onlineshopping.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private OrderDao orderDao;

    private ProductService productService;

    private ItemDao itemDao;

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Transactional
    public boolean placeOrder(List<Item> items, Integer userId) {
        // get item details from product, check if quantity is available
        // TODO raise exception if quantity is not available
        if (!checkQuantity(items)) return false;
        // create new order, set to processing, add new order
        orderDao.createOrder(userId);
        // get recent order ID
        Integer orderId = orderDao.getCreatedOrderId(userId);
        for (Item item : items) {
            Product product = productService.getProductById(item.getProductId());
            // update product quantity
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productService.updateProduct(product);
            // update item details
            item.setPurchasedPrice(product.getRetailPrice());
            item.setWholesalePrice(product.getWholesalePrice());
            item.setOrderId(orderId);
        }
        // save items to order
        itemDao.addItemsToOrder(items);
        return true;
    }
    public boolean checkQuantity(List<Item> items) {
        for (Item item : items) {
            Product product = productService.getProductById(item.getProductId());
            if (product.getQuantity() < item.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderDao.getOrdersByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public List<Item> getItemsByOrderId(Integer orderId) {
        return itemDao.getItemsByOrderId(orderId);
    }

    public Order getOrdersByOrderId(Integer id) {
        return orderDao.getOrdersByOrderId(id);
    }

    public Order loadOrder(Order order) {
        List<Item> items = getItemsByOrderId(order.getOrderId());
        for (Item i : items) {
            i.setProduct(productService.getProductById(i.getProductId()));
        }
        order.setItems(items);
        return order;
    }

    @Transactional
    public boolean cancelOrder(Order order) {
        if (order.getOrderStatus().equals("Processing")) {
            order.setOrderStatus("Cancelled");
            orderDao.updateOrder(order);
            // TODO update product quantity
            order = loadOrder(order);
            for (Item item : order.getItems()) {
                Product product = item.getProduct();
                product.setQuantity(product.getQuantity() + item.getQuantity());
                productService.updateProduct(product);
            }
            return true;
        } else {
            return false;
        }

    }

    public boolean completeOrder(Order order) {
        if (order.getOrderStatus().equals("Processing")) {
            order.setOrderStatus("Completed");
            orderDao.updateOrder(order);
            return true;
        } else {
            return false;
        }
    }

}
