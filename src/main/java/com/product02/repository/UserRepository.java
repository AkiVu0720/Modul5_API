package com.product02.repository;

import com.product02.model.entity.UserEntity;
import com.product02.payload.response.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByUserName(String userName);
    List<UserEntity> findByFullNameContainsIgnoreCase(String name);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
