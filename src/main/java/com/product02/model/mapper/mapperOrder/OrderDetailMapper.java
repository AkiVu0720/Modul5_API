package com.product02.model.mapper.mapperOrder;

import com.product02.model.entity.OrderDetailEntity;
import com.product02.model.mapper.IMapperGeneric;
import com.product02.payload.response.OrderDetailResponse;
import com.product02.payload.response.OrderViewResponse;
import com.product02.payload.response.ShoppingCartResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper implements IMapperGeneric<OrderDetailEntity, ShoppingCartResponse, OrderDetailResponse> {

    @Override
    public OrderDetailEntity requestToEntity(ShoppingCartResponse shoppingCartResponse) {
        return null;
    }

    @Override
    public OrderDetailResponse EntityToResponse(OrderDetailEntity orderDetailEntity) {
        return OrderDetailResponse.builder()
                .id(orderDetailEntity.getProductEntity().getProductId())
                .name(orderDetailEntity.getName())
                .quantity(orderDetailEntity.getQuantity())
                .price(orderDetailEntity.getPrice())
                .build();
    }
}
