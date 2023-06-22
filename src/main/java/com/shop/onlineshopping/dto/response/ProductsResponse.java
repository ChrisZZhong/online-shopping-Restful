package com.shop.onlineshopping.dto.response;

import com.shop.onlineshopping.domain.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductsResponse {
    private String status;
    private String message;
    private List<Product> products;
}
