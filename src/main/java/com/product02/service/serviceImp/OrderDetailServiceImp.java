package com.product02.service.serviceImp;

import com.product02.exception.CustomException;
import com.product02.model.entity.OrderDetailEntity;
import com.product02.model.entity.OrdersEntity;
import com.product02.model.entity.ShoppingCardEntity;
import com.product02.model.mapper.mapperOrder.OrderDetailMapper;
import com.product02.model.mapper.mapperOrder.OrderMapper;
import com.product02.payload.response.OrderDetailResponse;
import com.product02.repository.OrderDetailRepository;
import com.product02.service.OrderDetailService;
import com.product02.service.OrderService;
import com.product02.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImp implements OrderDetailService {
    @Autowired
    @Lazy
    private OrderService orderService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderDetailMapper orderMapper;
    @Override
    public OrderDetailEntity shoppingCartToOderDetail(long orderId, ShoppingCardEntity shoppingCard){
        OrdersEntity orders = orderService.findById(orderId);
        return  OrderDetailEntity.builder()
                .ordersEntity(orders)
                .productEntity(shoppingCard.getProduct())
                .name(shoppingCard.getProduct().getProductName())
                .price(shoppingCard.getProduct().getUnitPrice())
                .quantity(shoppingCard.getQuantity())
                .build();
    }
    @Override
    public List<OrderDetailEntity> orderDetailEntityList(List<Integer> listCartId,long orderId){
        List<ShoppingCardEntity> list = shoppingCartService.listShoppingCartByCartId(listCartId);
        List<OrderDetailEntity> orderDetailList = list.stream()
                .map( shoppingCardEntity -> orderDetailRepository
                        .save(shoppingCartToOderDetail(orderId,shoppingCardEntity)))
                .collect(Collectors.toList());
        return orderDetailList;
    }

    /**
     * Tính tiền cho 1 đơn hàng Order
     * @param orderId
     * @return
     */
    @Override
    public BigDecimal sumTotalByOrder(long orderId) {
        try {
        return orderDetailRepository.sumTotalByOderId(orderId);
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public List<OrderDetailResponse> findByIdRes(long orderId) {
        try {
            List<OrderDetailEntity> entityList = orderDetailRepository.findByOrdersEntity_OrderId(orderId);
            List<OrderDetailResponse> responseList = entityList.stream().map(orderDetail -> orderMapper.EntityToResponse(orderDetail)).collect(Collectors.toList());
            return responseList;
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }

    }
}
