package com.shop.onlineshopping.dto.response.ProductRespons;
import com.shop.onlineshopping.domain.Product;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductResponse {
    private String status;
    private String message;
    private Product product;
}
