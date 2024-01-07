package com.product02.service.serviceImp;

import com.product02.exception.CustomException;
import com.product02.model.entity.ERoles;
import com.product02.model.entity.RolesEntity;
import com.product02.payload.response.RoleResponse;
import com.product02.repository.RolesRepository;
import com.product02.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolesServiceImp implements RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public Set<RoleResponse> findAllRole() {
        Set<RolesEntity> rolesEntityList = rolesRepository.findAll().stream().collect(Collectors.toSet());
        Set<RoleResponse> roleResponsesList = rolesEntityList.stream()
                .map(rolesEntity -> RoleResponse.builder().roleId(rolesEntity.getId())
                        .name(rolesEntity.getName()).build()
                ).collect(Collectors.toSet());
        return roleResponsesList;
    }

    @Override
    public Optional<RolesEntity> findByName(ERoles name) {
        return rolesRepository.findByName(name);
    }

    @Override
    public RolesEntity findById(long id) {
        return rolesRepository.findById(id).get();
    }

    @Override
    public boolean deleteRoleByUserIdAndAndRoleId(long userId, long roleId) {
        try {
            if (findById(roleId)==null){
                throw new CustomException("Role Not Exist");
            }else {
                rolesRepository.deleteRoleByUserIdAndAndRoleId(userId,roleId);
                return true;
            }
        } catch (Exception e){
            throw new CustomException("Loi xay ra khi xoa role :"+ e.getMessage());
        }
    }

}
