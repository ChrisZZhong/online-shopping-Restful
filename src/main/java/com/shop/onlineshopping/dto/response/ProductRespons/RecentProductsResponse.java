package com.shop.onlineshopping.dto.response.ProductRespons;

import com.shop.onlineshopping.dto.response.ProductRespons.domain.RecentProduct;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentProductsResponse {
    private String status;
    private String message;
    private List<RecentProduct> recentProducts;
}
