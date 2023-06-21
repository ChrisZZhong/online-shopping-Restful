package com.shop.onlineshopping.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
}
