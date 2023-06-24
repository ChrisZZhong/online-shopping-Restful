package com.shop.onlineshopping.dto.response.ProductRespons.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class RecentProduct {
    private Integer productId;
    private String productName;
    private String description;
    private Date datePurchased;
}
