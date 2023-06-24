package com.shop.onlineshopping.dto.response.ProductRespons;

import com.shop.onlineshopping.dto.response.ProductRespons.domain.FrequentProduct;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrequentProductsResponse {
    private String status;
    private String message;
    private List<FrequentProduct> frequentProducts;
}
