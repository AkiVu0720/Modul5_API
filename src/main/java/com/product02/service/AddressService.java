package com.product02.service;

import com.product02.model.entity.AddressEntity;
import com.product02.payload.requet.AddressRequest;
import com.product02.payload.response.AddressResponse;

import java.util.Set;

public interface AddressService {
    AddressResponse save (AddressRequest addressRequest, long userId );
    Set<AddressResponse> findListByUserId(long userId);
    AddressResponse findByUserAndAddressId(long userId, long addressId);
    AddressEntity findByUserAndAddressIdEntity(long userId, long addressId);
}
