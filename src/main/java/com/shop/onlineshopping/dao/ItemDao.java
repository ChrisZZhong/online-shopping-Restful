package com.shop.onlineshopping.dao;

import com.shop.onlineshopping.domain.Item;
import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.domain.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class ItemDao extends AbstractHibernateDao<Item>{
    public ItemDao() {
        setClazz(Item.class);
    }

    public void addItemsToOrder(List<Item> items) {
        for (Item item : items) {
            add(item);
        }
    }

    public List<Item> getItemsByOrderId(Integer orderId) {
        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
        Root<Item> root = criteriaQuery.from(Item.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("orderId"), orderId));
        return session.createQuery(criteriaQuery).getResultList();

    }
}
