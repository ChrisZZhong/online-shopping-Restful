package com.shop.onlineshopping.dao;

import com.shop.onlineshopping.domain.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductDao extends AbstractHibernateDao<Product> {
    public ProductDao() {
        setClazz(Product.class);
    }

    public void addProduct(Product product) {
        add(product);
    }
}
