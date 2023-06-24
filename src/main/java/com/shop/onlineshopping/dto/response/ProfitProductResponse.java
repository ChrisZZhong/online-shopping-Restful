package com.shop.onlineshopping.dto.response;

import com.shop.onlineshopping.dto.PopularProduct;
import com.shop.onlineshopping.dto.ProfitProduct;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfitProductResponse {
    private String status;
    private String message;
    private List<ProfitProduct> profitProducts;
}
