package com.product02.payload.requet;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
}
