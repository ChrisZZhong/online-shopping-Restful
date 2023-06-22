package com.shop.onlineshopping.dao;

import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.domain.User;
import com.shop.onlineshopping.dto.request.SignUpRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
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

    public void signUp(User user) {
        add(user);
    }

    public Integer getUserIdByUsername(String username) {
        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root.get("userId"));
        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));
        return session.createQuery(criteriaQuery).getSingleResult();
    }

    public List<Product> getWatchlistProducts(Integer userId) {
        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root.get("watchlist"));
        criteriaQuery.where(criteriaBuilder.equal(root.get("userId"), userId));
        return session.createQuery(criteriaQuery).getResultList();
    }

    public User getUserById(Integer userId) {
        return findById(userId);
    }

    public void updateUser(User user) {
        getCurrentSession().saveOrUpdate(user);
    }
}
