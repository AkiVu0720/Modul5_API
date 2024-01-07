package com.product02.model.mapper.mapperOrder;

import com.product02.model.entity.OrderDetailEntity;
import com.product02.model.entity.OrdersEntity;
import com.product02.model.mapper.IMapperGeneric;
import com.product02.payload.requet.OrderRequest;
import com.product02.payload.response.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderMapper implements IMapperGeneric<OrdersEntity, OrderRequest, OrderResponse> {
    @Override
    public OrdersEntity requestToEntity(OrderRequest orderRequest) {
        return null;
    }

    @Override
    public OrderResponse EntityToResponse(OrdersEntity orders) {
        return OrderResponse.builder()
                .orderId(orders.getOrderId())
                .serialNumber(orders.getSerialNumber())
                .orderAt(orders.getOrderAt())
                .price(orders.getPrice())
                .receiveName(orders.getReceiveName())
                .receiveAddress(orders.getReceiveAddress())
                .receivePhone(orders.getReceivePhone())
                .created(orders.getCreated())
                .received(orders.getReceived())
                .build();
    }
    public OrderHistoryResponse EntityToOrderHistory(OrdersEntity orders){
        return OrderHistoryResponse.builder()
                .orderId(orders.getOrderId())
                .serialNumber(orders.getSerialNumber())
                .price(orders.getPrice())
                .status(orders.getStatus())
                .orderAt(orders.getOrderAt())
                .updateDay(LocalDateTime.now())
                .build();
    }
    public OrderByStatusResponse EntityToOrderByStatus(OrdersEntity orders){
        return OrderByStatusResponse.builder()
                .orderId(orders.getOrderId())
                .serialNumber(orders.getSerialNumber())
                .status(orders.getStatus())
                .build();
    }
    public OrderViewResponse EntityToViewResponse(OrderResponse order, List<OrderDetailResponse> orderDetailList){
        return OrderViewResponse.builder()
                .order(order)
                .detailList(orderDetailList)
                .build();
    }

}
