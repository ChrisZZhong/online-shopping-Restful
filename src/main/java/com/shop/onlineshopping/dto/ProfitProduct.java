package com.shop.onlineshopping.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class ProfitProduct {
    private Integer productId;
    private String name;
    private String description;
    private Double profit;
}
