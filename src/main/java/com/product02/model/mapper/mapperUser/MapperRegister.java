package com.product02.model.mapper.mapperUser;

import com.product02.model.entity.ERoles;
import com.product02.model.mapper.IMapperGeneric;
import com.product02.payload.requet.RegisterRequest;
import com.product02.payload.response.RegisterResponse;
import com.product02.model.entity.RolesEntity;
import com.product02.model.entity.UserEntity;
import com.product02.repository.RolesRepository;
import com.product02.service.RolesService;
import com.product02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class MapperRegister implements IMapperGeneric<UserEntity, RegisterRequest, RegisterResponse> {
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolesService rolesService;
    public UserEntity requestToEntity(RegisterRequest registerRequest) {
        RolesEntity userRole = rolesService.findByName(ERoles.ROLE_USER)
                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
        Set<RolesEntity> listRoles = new HashSet<>();
        listRoles.add(userRole);
        return UserEntity.builder()
                .fullName(registerRequest.getFullName())
                .userName(registerRequest.getUserName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .status(true)
                .created(new Date())
                .phone(registerRequest.getPhone())
                .address(registerRequest.getAddress())
                .listRoles(listRoles)
                .build();
    }

    @Override
    public RegisterResponse EntityToResponse(UserEntity userEntity) {
        return RegisterResponse.builder()
                .id(userEntity.getId())
                .userName(userEntity.getUserName())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .address(userEntity.getAddress())
                .build();
    }
}
