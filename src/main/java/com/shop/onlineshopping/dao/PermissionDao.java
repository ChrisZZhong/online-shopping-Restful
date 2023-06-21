package com.shop.onlineshopping.dao;

import com.shop.onlineshopping.domain.Permission;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PermissionDao extends AbstractHibernateDao<Permission> {
    public PermissionDao() {
        setClazz(Permission.class);
    }
    public void addPermission(Permission permission) {
        add(permission);
    }
}
