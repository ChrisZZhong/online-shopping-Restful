package com.shop.onlineshopping.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SignUpRequest {
    @NotNull()
    private String username;
    @NotNull()
    private String password;

    private String email;
}
