package com.shop.onlineshopping.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String description;
    private Integer quantity;
    private Double retailPrice;
    private Double wholesalePrice;
}
