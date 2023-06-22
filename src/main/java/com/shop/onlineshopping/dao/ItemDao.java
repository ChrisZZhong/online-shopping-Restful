package com.shop.onlineshopping.dao;

import com.shop.onlineshopping.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDao extends AbstractHibernateDao<Item>{
    public ItemDao() {
        setClazz(Item.class);
    }

    public void addItemsToOrder(List<Item> items) {
        for (Item item : items) {
            add(item);
        }
    }
}
