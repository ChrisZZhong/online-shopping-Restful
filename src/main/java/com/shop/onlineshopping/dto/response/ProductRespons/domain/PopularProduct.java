package com.shop.onlineshopping.dto.response.ProductRespons.domain;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class PopularProduct {
    private Integer productId;
    private String productName;
    private String description;
    private Long sales;
}
