package com.shop.onlineshopping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.onlineshopping.domain.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderRequest {
    @JsonProperty(value = "order")
    private List<Item> items;
}
