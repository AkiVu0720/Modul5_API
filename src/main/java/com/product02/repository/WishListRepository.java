package com.product02.repository;

import com.product02.model.entity.ProductEntity;
import com.product02.model.entity.WishListEntity;
import com.product02.payload.requet.WishListRequest;
import com.product02.payload.response.ProductResponse;
import com.product02.payload.response.WishListFeatured;
import com.product02.payload.response.WishListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishListEntity,Long> {
    List<WishListEntity> findByUserWish_Id(long userId);
//    @Query("select w.productWish.productId ,count (w.productWish.productId)from WishListEntity w group by w.productWish.productId order by count (w.productWish.productId) DESC limit 10")
    @Query("SELECT  new com.product02.payload.response.WishListFeatured(w.productWish.productId,count (w.productWish.productId)) " +
            "from WishListEntity w join ProductEntity pr on w.productWish.productId=pr.productId where pr.status =true " +
            "group by w.productWish order by count (w.productWish.productId) DESC limit 10")
    List<WishListFeatured> listFeaturedProduct();
    WishListEntity findByProductWish_ProductId(long productId);
    WishListEntity findByIdAndUserWish_Id(long wishId, long userId);
}
