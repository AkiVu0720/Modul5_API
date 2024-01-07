package com.product02.service;

import com.product02.payload.requet.LoginRequest;
import com.product02.payload.requet.RegisterRequest;
import com.product02.payload.response.LoginResponse;
import com.product02.payload.response.RegisterResponse;

public interface LoginService {
    RegisterResponse saveOrUpdate(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);

}
