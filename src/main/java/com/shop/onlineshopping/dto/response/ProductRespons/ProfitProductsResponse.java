package com.shop.onlineshopping.dto.response.ProductRespons;

import com.shop.onlineshopping.dto.response.ProductRespons.domain.ProfitProduct;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfitProductsResponse {
    private String status;
    private String message;
    private List<ProfitProduct> profitProducts;
}
