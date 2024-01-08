package com.product02.service;

import com.product02.model.entity.OrderDetailEntity;
import com.product02.model.entity.ShoppingCardEntity;
import com.product02.payload.response.OrderDetailResponse;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDetailService {
    OrderDetailEntity shoppingCartToOderDetail(long orderId, ShoppingCardEntity shoppingCard);
    List<OrderDetailEntity> orderDetailEntityList(List<Integer> listCartId, long orderId);
    BigDecimal sumTotalByOrder(long orderId);
    List<OrderDetailResponse> findByIdRes(long orderId);
    List<ShoppingCardEntity> listShoppingCard(List<Integer> listCartId);
}
