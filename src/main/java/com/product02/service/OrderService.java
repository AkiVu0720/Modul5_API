package com.product02.service;

import com.product02.model.entity.EStatus;
import com.product02.model.entity.OrderDetailEntity;
import com.product02.model.entity.OrdersEntity;
import com.product02.model.entity.ProductEntity;
import com.product02.payload.requet.OrderRequest;
import com.product02.payload.requet.OrderStatusRe;
import com.product02.payload.response.OrderByStatusResponse;
import com.product02.payload.response.OrderHistoryResponse;
import com.product02.payload.response.OrderResponse;
import com.product02.payload.response.OrderViewResponse;

import java.util.List;

public interface OrderService {
    OrdersEntity createOder(long userId,OrderRequest orderRequest);
    OrdersEntity findById(long oderId);
    OrderResponse findByIdRes(long oderId);
    OrdersEntity findBySerialNumber(String serialNumber);
    OrderViewResponse newOrder(long userId,OrderRequest orderRequest);
    List<OrderResponse> findAllOrder();
    List<OrderHistoryResponse> findOrderByUserId(long userId);
    OrdersEntity findByOrderIdAndStatus(long orderId,EStatus eStatus);
    List<OrderHistoryResponse> findOrderByUserIdAndStatus(long userId, String oderStatus);
    OrderHistoryResponse cancelOder(long userId, long orderId );
    OrdersEntity findByOrderIdAndUserOder_Id(long orderId, long userId);
    List<OrderByStatusResponse> findByStatus(String oderStatus);
    OrderViewResponse viewOrder(long orderId);
    OrderHistoryResponse updateStatusOrder(long order, String orderStatusRe);
}
