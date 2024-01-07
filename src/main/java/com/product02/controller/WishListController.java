package com.product02.controller;

import com.product02.model.entity.WishListEntity;
import com.product02.payload.requet.WishListRequest;
import com.product02.payload.response.BaseResponse;
import com.product02.payload.response.UserResponse;
import com.product02.payload.response.WishListResponse;
import com.product02.service.WishService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${global.url}/v1")
public class WishListController {
    @Autowired
    private WishService wishService;
    @GetMapping("/wish-list")
    public ResponseEntity<?> findUserById(
            @RequestParam long userid
    ){
        List<WishListResponse> wishListResponses = wishService.listAllByUserId(userid);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(wishListResponses);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PostMapping("/wish-list")
    public ResponseEntity<?> addWishList(@RequestBody WishListRequest wishListRequest){
        WishListResponse wishListResponses = wishService.addWishList(wishListRequest);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(wishListResponses);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @DeleteMapping("/wish-list/{wishListId}")
    public ResponseEntity<?> deleteProductInWishList(@PathVariable long wishListId){
        boolean isSuccess = wishService.delete(wishListId);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(isSuccess);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
