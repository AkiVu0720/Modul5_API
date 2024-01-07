package com.product02.repository;

import com.product02.model.entity.AddressEntity;
import com.product02.payload.response.AddressResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
    @Query("select a from AddressEntity as a where a.userAddr.id = ?1")
    Set<AddressEntity> findByUserAddrId(long userId);
    AddressEntity findByAddressIdAndUserAddr_Id(long userId, long addressId);

}
