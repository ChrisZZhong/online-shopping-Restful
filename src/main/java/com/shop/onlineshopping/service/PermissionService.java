package com.shop.onlineshopping.service;

import com.shop.onlineshopping.dao.PermissionDao;
import com.shop.onlineshopping.domain.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    private PermissionDao permissionDao;
    @Autowired
    public PermissionService(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    public void addPermission(Permission permission) {
        permissionDao.addPermission(permission);
    }
}
