package com.product02.controller;

import com.product02.model.entity.UserEntity;
import com.product02.model.mapper.mapperUser.UserMapper;
import com.product02.payload.requet.AddressRequest;
import com.product02.payload.requet.UploadPassRequest;
import com.product02.payload.requet.UserRequest;
import com.product02.payload.response.AddressResponse;
import com.product02.payload.response.BaseResponse;
import com.product02.payload.response.UserResponse;
import com.product02.service.AddressService;
import com.product02.service.RolesService;
import com.product02.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("${global.url}/v1")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/account/{id}")
    public ResponseEntity<?> findUserById(@Valid @PathVariable long id){
        UserResponse userResponse = userService.findByIdToUserResponse(id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(userResponse);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PutMapping("/account/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable long id, @RequestBody UserRequest userRequest){
        BaseResponse baseResponse = new BaseResponse();
        UserResponse userResponse = userService.update(id,userRequest);
        baseResponse.setStatusCode(200);
        baseResponse.setData(userResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PutMapping("/account/{id}/change-password")
    public ResponseEntity<?> changePassword(
            @Valid
            @PathVariable long id,
            @RequestBody UploadPassRequest passRequest){
        BaseResponse baseResponse = new BaseResponse();
        boolean isSuccess = userService.uploadPass(id,passRequest);
        baseResponse.setStatusCode(200);
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PostMapping("/account/{id}/address")
    public ResponseEntity<?>addAddress(
            @Valid
            @PathVariable long id,
            @RequestBody AddressRequest addressRequest
            ){
        AddressResponse addressResponse = addressService.save(addressRequest,id);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatusCode(200);
            baseResponse.setData(addressResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @GetMapping("/account/{userid}/address")
    public ResponseEntity<?>findAddressById(@PathVariable long userid){
        Set<AddressResponse> addressList = addressService.findListByUserId(userid);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatusCode(200);
            baseResponse.setData(addressList);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @GetMapping("/account/{userid}/address/{addressid}")
    public ResponseEntity<?>findAddressById(@PathVariable long userid, @PathVariable long addressid){
        AddressResponse addressResponse = addressService.findByUserAndAddressId(userid,addressid);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(addressResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @GetMapping("/admin/users")
    public ResponseEntity<?> findAllUser(
            @RequestParam (defaultValue = "0")int page,
            @RequestParam (defaultValue = "3") int size,
            @RequestParam (defaultValue = "ASC") String idDirection,
            @RequestParam (defaultValue = "ASC") String nameDirection,
            @RequestParam (defaultValue = "ASC") String statusDirection
    ){
        Page<UserEntity> entityList = userService.sortByNameAndUser(page,size,idDirection,nameDirection,statusDirection);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(entityList.getContent());
        Map<String, Object> dataResponse = new HashMap<>();
        dataResponse.put("totalPage", entityList.getTotalPages());
        dataResponse.put("totalUser", entityList.getTotalElements());
        dataResponse.put("dataUsers", baseResponse);
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<?> unlockAccount(@PathVariable long userId){
        boolean isSuccess = userService.unlockStatus(userId);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setStatusCode(200);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("test/{userId}")
    public ResponseEntity<?> testNewRole(
            @PathVariable long userId,
            @RequestParam long roleId
    ){
        boolean userResponse = rolesService.deleteRoleByUserIdAndAndRoleId(userId,roleId);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(userResponse);
        baseResponse.setStatusCode(200);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @GetMapping("admin/users/search")
    public ResponseEntity<?> searchName(
            @RequestParam String userName
    ){
        List<UserResponse> userResponse = userService.findByFullNameContaining(userName);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(userResponse);
        baseResponse.setStatusCode(200);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
