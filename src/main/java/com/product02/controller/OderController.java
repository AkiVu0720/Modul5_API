package com.product02.controller;

import com.product02.model.entity.OrdersEntity;
import com.product02.model.entity.ProductEntity;
import com.product02.payload.requet.OrderStatusRe;
import com.product02.payload.response.*;
import com.product02.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${global.url}/v1")
public class OderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("admin/orders")
    public ResponseEntity<?> findProductPermitById(){
        BaseResponse baseResponse = new BaseResponse();
        List<OrderResponse> orderResponses = orderService.findAllOrder();
        baseResponse.setStatusCode(200);
        baseResponse.setData(orderResponses);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @GetMapping("account/{userId}/history")
    public ResponseEntity<?> findOrderByUserId(
            @PathVariable long userId
    ){
        BaseResponse baseResponse = new BaseResponse();
        List<OrderHistoryResponse> orderResponses = orderService.findOrderByUserId(userId);
        baseResponse.setStatusCode(200);
        baseResponse.setData(orderResponses);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @GetMapping("account/{userId}/history/{orderStatus}")
    public ResponseEntity<?> findOrderByUserId(
            @PathVariable long userId,
            @PathVariable String orderStatus

    ){
        BaseResponse baseResponse = new BaseResponse();
        List<OrderHistoryResponse> orderResponses = orderService.findOrderByUserIdAndStatus(userId,orderStatus);
        baseResponse.setStatusCode(200);
        baseResponse.setData(orderResponses);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PutMapping("account/{userId}/history/{orderId}/cancel")
    public ResponseEntity<?> findOrderByUserId(
            @PathVariable long userId,
            @PathVariable long orderId
    ){
        BaseResponse baseResponse = new BaseResponse();
        OrderHistoryResponse orderResponses = orderService.cancelOder(userId,orderId);
        baseResponse.setStatusCode(200);
        baseResponse.setData(orderResponses);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @GetMapping("admin/orders/{orderStatus}")
    public ResponseEntity<?> findByStatus(
            @PathVariable String orderStatus
    ){
        BaseResponse baseResponse = new BaseResponse();
        List<OrderByStatusResponse> orderResponses = orderService.findByStatus(orderStatus);
        baseResponse.setStatusCode(200);
        baseResponse.setData(orderResponses);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Chi tiết đơn hàng theo orderId
     * @param oderId
     * @return
     */
    @GetMapping("admin/order/{oderId}")
    public ResponseEntity<?> findById(
            @PathVariable long oderId
    ){
            BaseResponse baseResponse = new BaseResponse();
            OrderViewResponse orderResponses = orderService.viewOrder(oderId);
            baseResponse.setData(orderResponses);
            baseResponse.setStatusCode(200);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PutMapping("admin/orders/{orderId}/status")
    public ResponseEntity<?> findById(
            @PathVariable long orderId,
            @RequestParam String orderStatus
    ){
        BaseResponse baseResponse = new BaseResponse();
        OrderHistoryResponse orderResponses = orderService.updateStatusOrder(orderId,orderStatus);
        baseResponse.setData(orderResponses);
        baseResponse.setStatusCode(200);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
