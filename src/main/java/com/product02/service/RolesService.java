package com.product02.service;

import com.product02.model.entity.ERoles;
import com.product02.model.entity.RolesEntity;
import com.product02.payload.response.RoleResponse;

import java.util.Optional;
import java.util.Set;

public interface RolesService {
    Set<RoleResponse> findAllRole();
    Optional<RolesEntity> findByName(ERoles name);
    RolesEntity findById(long id);
    boolean deleteRoleByUserIdAndAndRoleId(long userId, long roleId)
;}
