package com.product02.controller;

import com.product02.exception.CustomException;
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
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * Thông tin người dùng
     * @param id
     * @return
     */
    @GetMapping("account/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> findUserById(
            @PathVariable long id
    ){
        UserResponse userResponse = userService.findByIdToUserResponse(id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Thông tin người dùng");
        baseResponse.setData(userResponse);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Cập nhập thông tin người dùng
     * @param id
     * @param userRequest
     * @return
     */
    @PutMapping("/account/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> update(
            @PathVariable long id,
            @RequestBody UserRequest userRequest
    ){
        try {
            BaseResponse baseResponse = new BaseResponse();
            UserResponse userResponse = userService.update(id,userRequest);
            baseResponse.setStatusCode(200);
            baseResponse.setMessage("Cập nhập thông tin người dùng");
            baseResponse.setData(userResponse);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }

    }

    /**
     * Thay đổi mật khẩu
     * @param id
     * @param passRequest
     * @return
     */
    @PutMapping("/account/{id}/change-password")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePassword(
            @Valid
            @PathVariable long id,
            @RequestBody UploadPassRequest passRequest){
        BaseResponse baseResponse = new BaseResponse();
        String isSuccess = userService.uploadPass(id,passRequest);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Đổi mật khẩu thành công");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * thêm địa chỉ mới
     * @param id
     * @param addressRequest
     * @return
     */
    @PostMapping("/account/{id}/address")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?>addAddress(
            @Valid
            @PathVariable long id,
            @RequestBody AddressRequest addressRequest
            ){
        AddressResponse addressResponse = addressService.save(addressRequest,id);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatusCode(200);
            baseResponse.setMessage("Thêm mới địa chỉ thành công");
            baseResponse.setData(addressResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * lấy ra danh sách địa chỉ của người dùng
     * @param userid
     * @return
     */
    @GetMapping("/account/{userid}/address")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?>findAddressById(@PathVariable long userid){
        Set<AddressResponse> addressList = addressService.findListByUserId(userid);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatusCode(200);
            baseResponse.setMessage("Danh sách địa chỉ");
            baseResponse.setData(addressList);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Lấy địa chỉ người dùng theo addressId
     * @param userid
     * @param addressid
     * @return
     */
    @GetMapping("/account/{userid}/address/{addressid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?>findAddressById(
            @PathVariable long userid,
            @PathVariable long addressid){
        AddressResponse addressResponse = addressService.findByUserAndAddressId(userid,addressid);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(addressResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Danh sách tất cả người dùng
     * @param page
     * @param size
     * @param idDirection
     * @param nameDirection
     * @param statusDirection
     * @return
     */
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAllUser(
            @RequestParam (defaultValue = "0")int page,
            @RequestParam (defaultValue = "3") int size,
            @RequestParam (defaultValue = "ASC") String idDirection,
            @RequestParam (defaultValue = "ASC") String nameDirection,
            @RequestParam (defaultValue = "ASC") String statusDirection
    ){
        Page<UserResponse> entityList = userService.sortByNameAndUser(page,size,idDirection,nameDirection,statusDirection);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Danh sách người dùng");
        baseResponse.setData(entityList.getContent());
        Map<String, Object> dataResponse = new HashMap<>();
        dataResponse.put("totalPage", entityList.getTotalPages());
        dataResponse.put("totalUser", entityList.getTotalElements());
        dataResponse.put("dataUsers", baseResponse);
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    /**
     * Khoá và mở  khoá người dùng
     * @param userId
     * @return
     */
    @PutMapping("/admin/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> unlockAccount(
            @PathVariable long userId
    ){
        boolean isSuccess = userService.unlockStatus(userId);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setMessage("Khoá và mở khoá user");
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

    /**
     * Tìm kiếm người dùng theo tên
     * @param userName
     * @return
     */
    @GetMapping("admin/users/search")
    @PreAuthorize("hasRole('ADMIN')")
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
