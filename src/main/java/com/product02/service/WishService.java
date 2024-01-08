package com.product02.service;

import com.product02.model.entity.WishListEntity;
import com.product02.payload.requet.WishListRequest;
import com.product02.payload.response.WishListFeatured;
import com.product02.payload.response.WishListResponse;
import com.product02.repository.WishListRepository;

import java.util.List;

public interface WishService {
    List<WishListEntity> findByUserWish_Id(long userId);
    List<WishListResponse> listAllByUserId(long userId);
    List<WishListEntity> listAll();
    WishListEntity findById(long id);
    WishListResponse addWishList(WishListRequest wishListRequest);
    boolean delete(long id,long userId);
    List<WishListFeatured> listFeaturedProduct();

}
