package com.shop.onlineshopping.dto.response;

import com.shop.onlineshopping.dto.PopularProduct;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopularProductsResponse {
    private String status;
    private String message;
    private List<PopularProduct> popularProducts;
}
