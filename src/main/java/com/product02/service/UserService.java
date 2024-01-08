package com.product02.service;

import com.product02.model.entity.RolesEntity;
import com.product02.payload.requet.RegisterRequest;
import com.product02.payload.requet.UploadPassRequest;
import com.product02.payload.requet.UserRequest;
import com.product02.payload.response.RegisterResponse;
import com.product02.model.entity.UserEntity;
import com.product02.payload.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    Page<UserEntity>findAll(Pageable pageable);
    Page<UserResponse> sortByNameAndUser(int page, int size,String idDirection, String nameDirection, String Direction);
    UserEntity findByUserName(String userName);
    List<UserResponse> findByFullNameContaining(String name);
    UserEntity findUserById(long id);
    UserResponse findByIdToUserResponse(long id);
    Set<RolesEntity> getListRoleFromRegister(Set<String> stringList);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    UserResponse update(long id, UserRequest userRequest);
    String uploadPass(long id, UploadPassRequest passRequest);
    RegisterResponse saveOrUpdate(RegisterRequest registerRequest);
    UserResponse addRoleById(long userId, long roleId);
    boolean deleteRole(long userId, long roleId);
    boolean unlockStatus(long userId);


}
