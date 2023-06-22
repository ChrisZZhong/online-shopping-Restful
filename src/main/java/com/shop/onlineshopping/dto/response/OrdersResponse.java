package com.shop.onlineshopping.dto.response;

import com.shop.onlineshopping.domain.Order;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponse {
    private String status;
    private String message;
    private List<Order> order;
}
