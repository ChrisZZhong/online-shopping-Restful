package com.shop.onlineshopping.dao;

import com.shop.onlineshopping.domain.Product;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ProductDao extends AbstractHibernateDao<Product> {
    public ProductDao() {
        setClazz(Product.class);
    }

    public void addProduct(Product product) {
        add(product);
    }

    public Product getProductById(int id) {
        return findById(id);
    }

    public void updateProduct(Product product) {
        getCurrentSession().update(product);
    }

    public List<Product> getAllProducts() {
        return getAll();
    }

}
