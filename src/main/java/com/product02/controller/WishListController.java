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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${global.url}/v1")
public class WishListController {
    @Autowired
    private WishService wishService;

    /**
     * Lấy ra danh sách yêu thích
     * @param userid
     * @return
     */
    @GetMapping("/wish-list")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> findUserById(
            @RequestParam long userid
    ){
        List<WishListResponse> wishListResponses = wishService.listAllByUserId(userid);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Danh sách yêu thích");
        baseResponse.setData(wishListResponses);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Thêm mới 1 sản phẩm vào danh sách yêu thích
     * @param wishListRequest
     * @return
     */
    @PostMapping("/wish-list")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addWishList(@RequestBody WishListRequest wishListRequest){
        WishListResponse wishListResponses = wishService.addWishList(wishListRequest);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Thêm mới sản phẩm vào danh sách yêu thích");
        baseResponse.setData(wishListResponses);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Xóa sản phẩm ra khỏi danh sách yêu thích
     * @param wishListId
     * @return
     */
    @DeleteMapping("/wish-list/{wishListId}")
    @PreAuthorize("hasRole('USER')"
    )
    public ResponseEntity<?> deleteProductInWishList(
            @PathVariable long wishListId,
            @RequestParam long userId
    ){
        boolean isSuccess = wishService.delete(wishListId, userId);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Xoá sản phẩm khỏi danh sách yêu thích");
        baseResponse.setData(isSuccess);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
