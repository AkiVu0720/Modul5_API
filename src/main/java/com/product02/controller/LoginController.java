package com.product02.controller;

import com.product02.payload.requet.LoginRequest;
import com.product02.payload.requet.RegisterRequest;
import com.product02.payload.response.BaseResponse;
import com.product02.payload.response.LoginResponse;
import com.product02.payload.response.RegisterResponse;
import com.product02.service.LoginService;
import com.product02.service.UserService;
import com.product02.utils.jwt.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${global.url}/v1/auth")
public class LoginController {
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        BaseResponse baseResponse = new BaseResponse();
        LoginResponse loginResponse=loginService.login(loginRequest);
        baseResponse.setMessage("Đăng nhập thành công");
        baseResponse.setStatusCode(200);
        baseResponse.setData(loginResponse);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        if (userService.existsByUserName(request.getUserName())){
            return ResponseEntity.badRequest().body("Username is exist");
        }
        if (userService.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body("Email is exist");
        }
        BaseResponse baseResponse = new BaseResponse();
        RegisterResponse response = userService.saveOrUpdate(request);
        baseResponse.setMessage("Đăng kí thành công");
        baseResponse.setStatusCode(200);
        baseResponse.setData(response);
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }
    @PostMapping("/user")
    public String userAccess(){
        return "User Access";
    }
    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String moderatorAccess(){
        return "Moderator Access";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess(){
        return "Admin Access";
    }
}
