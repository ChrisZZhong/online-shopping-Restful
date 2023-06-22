package com.shop.onlineshopping.service;

import com.shop.onlineshopping.dao.ProductDao;
import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.dto.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductDao productDao;

    @Autowired
    public void setProductDao(ProductDao productDao){
        this.productDao = productDao;
    }

    public void addProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .quantity(productRequest.getQuantity())
                .wholesalePrice(productRequest.getWholesalePrice())
                .retailPrice(productRequest.getRetailPrice())
                .build();
        productDao.addProduct(product);
    }

    @Transactional
    public void updateProduct(ProductRequest productRequest, int id) {
        Product product = productDao.getProductById(id);
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setQuantity(productRequest.getQuantity());
        product.setWholesalePrice(productRequest.getWholesalePrice());
        product.setRetailPrice(productRequest.getRetailPrice());
        productDao.updateProduct(product);
    }


    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public List<Product> getOnsaleProducts() {
        return productDao.getAllProducts().stream().filter(product -> product.getQuantity() > 0).collect(Collectors.toList());
    }

    public Product getProductById(Integer id) {
        return productDao.getProductById(id);
    }
}
