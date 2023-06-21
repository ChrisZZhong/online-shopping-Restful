package com.shop.onlineshopping.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {
    private String status;
    private String message;
    private String token;

}
