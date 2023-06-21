package com.shop.onlineshopping.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatusResponse {
    private String status;
    private String message;
}
