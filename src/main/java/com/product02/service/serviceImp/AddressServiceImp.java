package com.product02.service.serviceImp;

import com.product02.exception.CustomException;
import com.product02.model.entity.AddressEntity;
import com.product02.model.entity.UserEntity;
import com.product02.model.mapper.AddressMapper;
import com.product02.payload.requet.AddressRequest;
import com.product02.payload.response.AddressResponse;
import com.product02.repository.AddressRepository;
import com.product02.service.AddressService;
import com.product02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AddressServiceImp implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressMapper addressMapper;
    @Override
    public AddressResponse save(AddressRequest addressRequest, long userId) {
        UserEntity user = userService.findUserById(userId);
        try {
            AddressEntity addressEntity =addressMapper.requestToEntity(addressRequest);
            addressEntity.setUserAddr(user);
            return addressMapper.EntityToResponse(addressRepository.save(addressEntity));
        } catch (Exception e){
            throw new CustomException("Loi xay Ra khi");
        }

    }
    @Override
    public Set<AddressResponse> findListByUserId(long userId) {
        userService.findUserById(userId);
        try {
            return addressRepository.findByUserAddrId(userId).stream().map(addressEntity -> addressMapper.EntityToResponse(addressEntity)).collect(Collectors.toSet());
        } catch (Exception e){
            throw new CustomException("Address not fount");
        }

    }

    /**
     * Chuyển địa chỉ sang response
     * @param userId
     * @param addressId
     * @return
     */

    @Override
    public AddressResponse findByUserAndAddressId(long userId, long addressId) {
        userService.findUserById(userId);
        try {
        return addressMapper.EntityToResponse(addressRepository.findByUserAddr_IdAndAddressId(userId,addressId));
        } catch (Exception e){
            throw new CustomException("Loi Address not Fount: "+ e.getMessage());
        }
    }

    /**
     * Lấy địa chỉ theo userId và addressID
     * @param userId
     * @param addressId
     * @return
     */

    @Override
    public AddressEntity findByUserAndAddressIdEntity(long userId, long addressId) {
        userService.findUserById(userId);
        try {
            return addressRepository.findByUserAddr_IdAndAddressId(userId,addressId);
        } catch (Exception e){
            throw new CustomException("Loi Address not Fount: "+ e.getMessage());
        }
    }
}
