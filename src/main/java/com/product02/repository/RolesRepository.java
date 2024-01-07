package com.product02.repository;

import com.product02.model.entity.ERoles;
import com.product02.model.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity,Long> {
    Optional<RolesEntity>findByName(ERoles name);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM User_Roles where user_id=:userId and role_id=:roleId", nativeQuery = true)
    void deleteRoleByUserIdAndAndRoleId(@Param("userId") long userId,@Param("roleId") long roleId);
}
