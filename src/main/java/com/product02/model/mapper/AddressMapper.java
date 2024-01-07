package com.product02.model.mapper;

import com.product02.model.entity.AddressEntity;
import com.product02.payload.requet.AddressRequest;
import com.product02.payload.response.AddressResponse;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements IMapperGeneric<AddressEntity, AddressRequest, AddressResponse>{
    @Override
    public AddressEntity requestToEntity(AddressRequest addressRequest) {
        return AddressEntity.builder()
                .fullAddress(addressRequest.getAddress())
                .phone(addressRequest.getPhone())
                .receiveName(addressRequest.getReceiveName())
                .build();
    }

    @Override
    public AddressResponse EntityToResponse(AddressEntity addressEntity) {
        return AddressResponse.builder()
                .id(addressEntity.getAddressId())
                .address(addressEntity.getFullAddress())
                .phone(addressEntity.getPhone())
                .receiveName(addressEntity.getReceiveName())
                .build();
    }
}
