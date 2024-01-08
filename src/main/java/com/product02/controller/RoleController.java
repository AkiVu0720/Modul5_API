package com.product02.controller;

import com.product02.payload.response.BaseResponse;
import com.product02.payload.response.RoleResponse;
import com.product02.payload.response.UserResponse;
import com.product02.service.RolesService;
import com.product02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("${global.url}/v1")
public class RoleController {
    @Autowired
    private UserService userService;
    @Autowired
    private RolesService rolesService;

    @PostMapping("/admin/users/{userId}/role")
    public ResponseEntity<?> addNewRole(
            @PathVariable long userId,
            @RequestParam long roleId
    ){
        UserResponse userResponse = userService.addRoleById(userId,roleId);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(userResponse);
        baseResponse.setStatusCode(200);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @DeleteMapping("/admin/users/{userId}/role")
    public ResponseEntity<?> deleteNewRole(
            @PathVariable long userId,
            @RequestParam long roleId
    ){
        boolean isSuccess = userService.deleteRole(userId,roleId);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setStatusCode(200);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Lấy về danh sách các quyên
     * @return
     */
    @GetMapping("/admin/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAllRole(){
        Set<RoleResponse> response =rolesService.findAllRole();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(response);
        baseResponse.setMessage("Danh sách Role");
        baseResponse.setStatusCode(200);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
