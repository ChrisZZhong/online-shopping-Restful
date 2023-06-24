package com.shop.onlineshopping.dao;

import com.shop.onlineshopping.domain.Item;
import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.dto.response.ProductRespons.domain.FrequentProduct;
import com.shop.onlineshopping.dto.response.ProductRespons.domain.PopularProduct;
import com.shop.onlineshopping.dto.response.ProductRespons.domain.ProfitProduct;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
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
        if (limit == 0) return session.createQuery(criteriaQuery).getResultList();
        return session.createQuery(criteriaQuery).setMaxResults(limit).getResultList();
    }

    public List<ProfitProduct> getTopProfitProducts(Integer limit) {
        /*
            select order_item.product_id, product.name, product.description, sum((order_item.purchased_price - order_item.wholesale_price) * (order_item.quantity)) as profit
            from order_item join orders, product
            where order_item.order_id = orders.order_id
            and product.product_id = order_item.product_id
            and orders.order_status = "Completed"
            group by order_item.product_id, product.name, product.description
            order by profit desc;
         */
        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ProfitProduct> criteriaQuery = criteriaBuilder.createQuery(ProfitProduct.class);
        Root<Item> root = criteriaQuery.from(Item.class);
        root.join("order", JoinType.INNER);
        root.join("product", JoinType.INNER);
        criteriaQuery.multiselect(
                root.get("product").get("productId"),
                root.get("product").get("name"),
                root.get("product").get("description"),
                criteriaBuilder.sum(
                        criteriaBuilder.prod(
                                criteriaBuilder.diff(root.get("purchasedPrice"), root.get("wholesalePrice")),
                                root.get("quantity")
                        )
                ).alias("profit")
        );
        criteriaQuery.where(criteriaBuilder.equal(root.get("order").get("orderStatus"), "Completed"));
        criteriaQuery.groupBy(root.get("product").get("productId"), root.get("product").get("name"), root.get("product").get("description"));
        criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(
                criteriaBuilder.prod(
                        criteriaBuilder.diff(root.get("purchasedPrice"), root.get("wholesalePrice")),
                        root.get("quantity")
                )
        )));
        if (limit == 0) return session.createQuery(criteriaQuery).getResultList();
        return session.createQuery(criteriaQuery).setMaxResults(limit).getResultList();

    }

    public List<FrequentProduct> getTopFrequentProductsByUserId(Integer limit, Integer userId) {
        /*
            select order_item.product_id, product.name, product.description, sum(order_item.quantity) as frequency
            from order_item join orders, product
            where order_item.order_id = orders.order_id
            and product.product_id = order_item.product_id
            and orders.order_status = "Completed"
            and user_id = 2
            group by order_item.product_id, product.name, product.description
            order by frequency desc;
         */
        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<FrequentProduct> criteriaQuery = criteriaBuilder.createQuery(FrequentProduct.class);
        Root<Item> root = criteriaQuery.from(Item.class);
        root.join("order", JoinType.INNER);
        root.join("product", JoinType.INNER);
        criteriaQuery.multiselect(
                root.get("product").get("productId"),
                root.get("product").get("name"),
                root.get("product").get("description"),
                criteriaBuilder.sum(root.get("quantity")).alias("frequency")
        );

        Predicate predicate1 = criteriaBuilder.equal(root.get("order").get("orderStatus"), "Completed");
        Predicate predicate2 = criteriaBuilder.equal(root.get("order").get("userId"), userId);
        criteriaQuery.where(criteriaBuilder.and(predicate1, predicate2));

        criteriaQuery.groupBy(root.get("product").get("productId"), root.get("product").get("name"), root.get("product").get("description"));

        criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(root.get("quantity"))));
        if (limit == 0) return session.createQuery(criteriaQuery).getResultList();
        return session.createQuery(criteriaQuery).setMaxResults(limit).getResultList();
    }
}
