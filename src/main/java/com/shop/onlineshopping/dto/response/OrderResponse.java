package com.shop.onlineshopping.dto.response;

import com.shop.onlineshopping.domain.Order;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String status;
    private String message;
    private Order order;
}
