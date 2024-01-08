package com.product02.service.serviceImp;

import com.product02.config.customSecurity.CustomUserDetail;
import com.product02.config.customSecurity.CustomUserDetailService;
import com.product02.exception.CustomException;
import com.product02.model.entity.UserEntity;
import com.product02.payload.requet.LoginRequest;
import com.product02.payload.requet.RegisterRequest;
import com.product02.payload.response.LoginResponse;
import com.product02.payload.response.RegisterResponse;
import com.product02.service.LoginService;
import com.product02.service.RolesService;
import com.product02.service.UserService;
import com.product02.utils.jwt.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class LoginServiceImp implements LoginService {
    @Autowired
    private UserService userService;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Override
    public RegisterResponse saveOrUpdate(RegisterRequest registerRequest) {
        return null;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        isUser(loginRequest);
        isBlocked(loginRequest);
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
                loginRequest.getUserName(), loginRequest.getPassword());
        //truyền userToken vào để so sánh
        authenticationManager.authenticate(user);
        CustomUserDetail customUserDetail = (CustomUserDetail) customUserDetailService.loadUserByUsername(user.getName());
        //Sinh JWT-Token tra ve client
        String jwt = jwtHelper.generateToken(customUserDetail.getUsername());
        //Lay cac quyen cua user
        List<String> listRoles = customUserDetail.getAuthorities().stream()
                .map(item->item.getAuthority()).collect(Collectors.toList());
        return new LoginResponse(jwt,"Bearer",customUserDetail.getUsername(),
                customUserDetail.getEmail(),customUserDetail.getPhone(),listRoles);

    }
    public boolean isUser(LoginRequest loginRequest){
        UserEntity user = userService.findByUserName(loginRequest.getUserName());
        if (user!=null&&passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            return true;
        }else {
            throw new CustomException("User not found");
        }
    }
    public boolean isBlocked(LoginRequest loginRequest){
        UserEntity user = userService.findByUserName(loginRequest.getUserName());
        if (user.isStatus()){
            return true;
        }else {
            throw new CustomException("User blocked");
        }

    }



}
