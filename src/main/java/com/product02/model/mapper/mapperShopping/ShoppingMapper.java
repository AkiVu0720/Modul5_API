package com.product02.model.mapper.mapperShopping;

import com.product02.model.entity.ShoppingCardEntity;
import com.product02.model.mapper.IMapperGeneric;
import com.product02.payload.requet.ShoppingCartRequest;
import com.product02.payload.response.ShoppingCartCreateResponse;
import com.product02.payload.response.ShoppingCartResponse;
import org.springframework.stereotype.Component;

@Component
public class ShoppingMapper implements IMapperGeneric<ShoppingCardEntity, ShoppingCartRequest, ShoppingCartResponse> {
    @Override
    public ShoppingCardEntity requestToEntity(ShoppingCartRequest shoppingCartRequest) {
        return ShoppingCardEntity.builder().build();
    }

    @Override
    public ShoppingCartResponse EntityToResponse(ShoppingCardEntity shoppingCardEntity) {
        return ShoppingCartResponse.builder()
                .productName(shoppingCardEntity.getProduct().getProductName())
                .quantity(shoppingCardEntity.getQuantity())
                .price(shoppingCardEntity.getQuantity()*shoppingCardEntity.getProduct().getUnitPrice().longValue())
                .build();
    }
    public ShoppingCartCreateResponse EntityToCreateShoppingCart(ShoppingCardEntity shoppingCardEntity){
        return ShoppingCartCreateResponse.builder()
                .cartId(shoppingCardEntity.getCartId())
                .userId(shoppingCardEntity.getUser().getId())
                .userName(shoppingCardEntity.getUser().getUserName())
                .build();
    }
}
