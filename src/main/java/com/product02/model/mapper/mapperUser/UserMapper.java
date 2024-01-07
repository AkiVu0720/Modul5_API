package com.product02.model.mapper.mapperUser;

import com.product02.model.entity.UserEntity;
import com.product02.model.mapper.IMapperGeneric;
import com.product02.payload.requet.UserRequest;
import com.product02.payload.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IMapperGeneric<UserEntity, UserRequest, UserResponse> {
    @Override
    public UserEntity requestToEntity(UserRequest userRequest) {
        return UserEntity.builder()
                .fullName(userRequest.getFullName())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .address(userRequest.getAddress())
                .build();
    }

    @Override
    public UserResponse EntityToResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .fullName(userEntity.getFullName())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .address(userEntity.getAddress())
                .created(userEntity.getCreated())
                .updated(userEntity.getUpdated())
                .status(userEntity.isStatus())
                .listRole(userEntity.getListRoles())
                .build();
    }
}
