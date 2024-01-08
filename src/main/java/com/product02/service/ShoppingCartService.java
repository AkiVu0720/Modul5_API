package com.product02.service;

import com.product02.model.entity.ShoppingCardEntity;
import com.product02.payload.requet.ShoppingCartRequest;
import com.product02.payload.requet.ShoppingCartUpdateQuantityResponse;
import com.product02.payload.response.ShoppingCartCreateResponse;
import com.product02.payload.response.ShoppingCartResponse;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCartResponse> listProductInCart(long userId);
    List<ShoppingCardEntity> listShoppingCartByUserId(long userId);
    ShoppingCardEntity findByCartIdAndUser_Id(long user, int cartId);
    List<ShoppingCardEntity> listShoppingCartByCartId(List<Integer>listShopping);
    boolean deleteShoppingCartByListCartId(long userId,List<Integer>listCartId);
    ShoppingCardEntity findById(int cartId);
    boolean deleteAllProductInCart(long userId);
    boolean deleteByCartId(long userId,int cartId);
    ShoppingCartCreateResponse createShoppingCard(long userId);
    ShoppingCartResponse addShoppingCart(long userId, ShoppingCartRequest cartRequest);
    ShoppingCardEntity findByUser_IdAndProduct_ProductId(long userId, long productId);
    ShoppingCartResponse update(long userId, int productId, ShoppingCartUpdateQuantityResponse cartRequest);
    ShoppingCardEntity findByUser_IdAndCartId(long userId, long cartId);
}
