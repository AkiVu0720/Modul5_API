package com.product02.model.mapper.mapperWishList;

import com.product02.model.entity.ProductEntity;
import com.product02.model.entity.UserEntity;
import com.product02.model.entity.WishListEntity;
import com.product02.model.mapper.IMapperGeneric;
import com.product02.payload.requet.WishListRequest;
import com.product02.payload.response.WishListResponse;
import com.product02.service.ProductService;
import com.product02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class WishListMapper implements IMapperGeneric<WishListEntity, WishListRequest, WishListResponse> {
    @Autowired
    @Lazy
    private ProductService productService;
    @Autowired
    @Lazy
    private UserService userService;
    @Override
    public WishListEntity requestToEntity(WishListRequest wishListRequest) {
        ProductEntity product = productService.findEntityById(wishListRequest.getProductId());
        UserEntity user = userService.findUserById(wishListRequest.getUserId());
        return WishListEntity.builder()
                .productWish(product)
                .userWish(user)
                .build();
    }

    @Override
    public WishListResponse EntityToResponse(WishListEntity wishListEntity) {
        return WishListResponse.builder()
                .productName(wishListEntity.getProductWish().getProductName())
                .build();
    }
}
