package com.shop.onlineshopping.service;

import com.shop.onlineshopping.dao.ProductDao;
import com.shop.onlineshopping.domain.Product;
import com.shop.onlineshopping.dto.response.ProductRespons.domain.FrequentProduct;
import com.shop.onlineshopping.dto.response.ProductRespons.domain.PopularProduct;
import com.shop.onlineshopping.dto.response.ProductRespons.domain.ProfitProduct;
import com.shop.onlineshopping.dto.request.ProductRequest;
import com.shop.onlineshopping.dto.response.ProductRespons.domain.RecentProduct;
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
    public void updateProductById(ProductRequest productRequest, int id) {
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

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    // pass by 0 means no limit
    public List<PopularProduct> getTopPopularProducts(Integer limit) {
        List<PopularProduct> popularProducts = productDao.getTopPopularProducts(limit);
        return popularProducts;
    }

    public List<ProfitProduct> getTopProfitProducts(Integer limit) {
        List<ProfitProduct> profitProducts = productDao.getTopProfitProducts(limit);
        return profitProducts;
    }

    public List<FrequentProduct> getTopFrequentProductsByUserId(Integer limit, Integer userId) {
        List<FrequentProduct> frequentProducts = productDao.getTopFrequentProductsByUserId(limit, userId);
        return frequentProducts;
    }

    public List<RecentProduct> getTopRecentProductsByUserId(Integer limit, Integer userId) {
        List<RecentProduct> recentProducts = productDao.getTopRecentProductsByUserId(limit, userId);
        return recentProducts;
    }
}
