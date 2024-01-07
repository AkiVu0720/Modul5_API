package com.product02.service.serviceImp;

import com.product02.exception.CustomException;
import com.product02.model.entity.ProductEntity;
import com.product02.model.entity.WishListEntity;
import com.product02.model.mapper.mapperWishList.WishListMapper;
import com.product02.payload.requet.WishListRequest;
import com.product02.payload.response.ProductResponse;
import com.product02.payload.response.WishListFeatured;
import com.product02.payload.response.WishListResponse;
import com.product02.repository.WishListRepository;
import com.product02.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishServiceImp implements WishService {
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private WishListMapper wishListMapper;
    @Override
    public List<WishListEntity> findByUserWish_Id(long userId) {
        List<WishListEntity> list = new ArrayList<>();
        list = wishListRepository.findByUserWish_Id(userId);
        if (list==null){
            throw new CustomException("Not Exist");
        }else {
            return list;
        }
    }

    @Override
    public List<WishListResponse> listAllByUserId(long userId) {
        List<WishListEntity> wishList = findByUserWish_Id(userId);
        return wishList.stream().map(wishListEntity -> wishListMapper.EntityToResponse(wishListEntity)).collect(Collectors.toList());
    }

    @Override
    public List<WishListEntity> listAll() {
        return wishListRepository.findAll();
    }

    @Override
    public WishListEntity findById(long id) {
        Optional<WishListEntity> wishListEntity = wishListRepository.findById(id);
        if (wishListEntity.isPresent()){
        return wishListRepository.findById(id).get();
        }else {
            throw new CustomException("Wish not Exist");
        }
    }

    @Override
    public WishListResponse addWishList(WishListRequest wishListRequest) {
        WishListEntity wishListEntity = wishListRepository.save(wishListMapper.requestToEntity(wishListRequest));
        return wishListMapper.EntityToResponse(wishListEntity);
    }

    @Override
    public boolean delete(long id) {
        try {
            WishListEntity wishList = findById(id);
            wishListRepository.delete(wishList);
            return true ;
        } catch (Exception e){
            throw new CustomException("Wish list not Exist");
        }

    }

    /**
     * Danh sách sản phẩm nổi bật được yêu thích.
     * @return
     */
    @Override
    public List<WishListFeatured> listFeaturedProduct() {
        List<WishListFeatured> list= wishListRepository.listFeaturedProduct();
        return list;
    }
}
