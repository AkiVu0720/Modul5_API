package com.product02.controller;

import com.product02.exception.CustomException;
import com.product02.model.entity.OrdersEntity;
import com.product02.model.entity.ShoppingCardEntity;
import com.product02.payload.requet.OrderRequest;
import com.product02.payload.requet.ShoppingCartRequest;
import com.product02.payload.requet.ShoppingCartUpdateQuantityResponse;
import com.product02.payload.response.*;
import com.product02.service.OrderService;
import com.product02.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${global.url}/v1")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService cartService;
    @Autowired
    private OrderService orderService;

    /**
     * Danh sách sản phẩm trong giỏ hàng
     * @param userId
     * @return
     */
    @GetMapping("shopping-cart/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> findListProductByUserId(@PathVariable long userId){
        BaseResponse baseResponse = new BaseResponse();
        List<ShoppingCartResponse> responseList = cartService.listProductInCart(userId);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Danh sách sản phẩm trong giỏ hàng");
        baseResponse.setData(responseList);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Tạo mới giỏ hàng.
     * @param userId
     * @return
     */
    @PostMapping("shopping-cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createShoppingCart(@RequestParam long userId){
        BaseResponse baseResponse = new BaseResponse();
        boolean isResult = false;
        ShoppingCartCreateResponse response = cartService.createShoppingCard(userId);
        if (response!=null){
            isResult=true;
        }
        baseResponse.setStatusCode(200);
        baseResponse.setData(isResult);
        baseResponse.setMessage("Tạo mới giỏ hàng");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Thêm mới sản phẩm vào giỏ hàng
     * @param userId
     * @param cartRequest
     * @return
     */
    @PostMapping("shopping-cart/{userId}/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addProductShoppingCart(@PathVariable long userId, @RequestBody ShoppingCartRequest cartRequest){

        BaseResponse baseResponse = new BaseResponse();
        ShoppingCartResponse cartResponse = cartService.addShoppingCart(userId,cartRequest);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Thêm sản phẩm vào giỏ hàng");
        baseResponse.setData(cartResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Xoá tất cả sản phẩm trong giỏ hàng
     * @param userId
     * @return
     */
    @DeleteMapping("shopping-cart/{userId}/clear")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteAllByUserId(
            @PathVariable long userId){
        BaseResponse baseResponse = new BaseResponse();
        boolean isSuccess = cartService.deleteAllProductInCart(userId);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("  Xoá tất cả sản phẩm trong giỏ hàng");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Xoá 1 sản phẩm trong giỏ hàng
     * @param userId
     * @param shoppingCartId
     * @return
     */
    @DeleteMapping("shopping-cart/{userId}/delete/{shoppingCartId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteByUserIdAndCartId(
            @PathVariable long userId,
            @PathVariable int shoppingCartId){
        BaseResponse baseResponse = new BaseResponse();
        boolean isSuccess = cartService.deleteByCartId(userId,shoppingCartId);
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Xoá 1 sản phẩm trong giỏ hàng");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Thay đổi số lượng sản phẩm
     * @param userId
     * @param cartItemId
     * @param cartRequest
     * @return
     */
    @PutMapping("shopping-cart/{userId}/update/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateQuantity(
            @PathVariable long userId,
            @PathVariable int cartItemId,
            @RequestBody ShoppingCartUpdateQuantityResponse cartRequest){
            BaseResponse baseResponse = new BaseResponse();
            ShoppingCartResponse response = cartService.update(userId,cartItemId,cartRequest);
            baseResponse.setStatusCode(200);
            baseResponse.setMessage("Thay đổi sốượng sản phẩm");
            baseResponse.setData(response);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("shopping-cart/{userId}/check-out")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> newOrder(
            @PathVariable long userId,
            @RequestBody OrderRequest orderRequest
    ){
        try {
            BaseResponse baseResponse = new BaseResponse();
            OrderViewResponse response = orderService.newOrder(userId,orderRequest);
            baseResponse.setStatusCode(200);
            baseResponse.setData(response);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }

    }

}
