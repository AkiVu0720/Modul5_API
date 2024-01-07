package com.product02.service.serviceImp;

import com.product02.exception.CustomException;
import com.product02.model.entity.*;
import com.product02.model.mapper.mapperOrder.OrderMapper;
import com.product02.payload.requet.OrderRequest;
import com.product02.payload.requet.OrderStatusRe;
import com.product02.payload.response.*;
import com.product02.repository.OrderRepository;
import com.product02.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

public class OrderServiceImp implements OrderService {
    static  String UUID_CODE = "";
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    @Lazy
    private ShoppingCartService cartService;
    @Autowired
    @Lazy
    private OrderDetailService  orderDetailService;
    @Autowired
    private OrderMapper orderMapper;
    /**
     * Khởi tạo đơn hàng
     */
    @Override
    public OrdersEntity createOder(long userId, OrderRequest orderRequest) {
        String uuid = UUID.randomUUID().toString();
        UUID_CODE = uuid;
        UserEntity user = userService.findUserById(userId);
        OrdersEntity orders = new OrdersEntity();
        orders.setNote(orderRequest.getNote());
        orders.setReceiveName(orderRequest.getName());
        orders.setReceiveAddress(orderRequest.getAddress());
        orders.setReceivePhone(orderRequest.getPhone());
        orders.setUserOder(user);
        orders.setSerialNumber(uuid);
        orders.setStatus(EStatus.WAITING);
        orders.setOrderAt(LocalDateTime.now());
        orders.setCreated(new Date());
        orders.setReceived(LocalDate.now().plusDays(4));
        orderRepository.save(orders);
        return orders;
    }
    @Override
    public OrderViewResponse newOrder(long userId,OrderRequest orderRequest){
        OrdersEntity orders = createOder(userId,orderRequest);
        List<OrderDetailEntity> list = orderDetailService.orderDetailEntityList(orderRequest.getListCartId(),orders.getOrderId());
        orders.setPrice(orderDetailService.sumTotalByOrder(orders.getOrderId()));
        orders.setOrderDetailEntityList(list);
        //Xoá sp trong giỏ hàng sau khi tạo Order.
        cartService.deleteShoppingCartByListCartId(userId,orderRequest.getListCartId());
        return viewOrder(orderRepository.save(orders).getOrderId());
    }

    /**
     *  Tìm  tất cả các sản phẩm.
     * @return
     */
    @Override
    public List<OrderResponse> findAllOrder() {
        List<OrdersEntity> ordersEntityList = orderRepository.findAll();
        return ordersEntityList.stream().map(orders -> orderMapper.EntityToResponse(orders)).collect(Collectors.toList());
    }

    /**
     * Lich su mua hang User_Role
     * @param userId
     * @return
     */
    @Override
    public List<OrderHistoryResponse> findOrderByUserId(long userId) {
        try {
            List<OrdersEntity>ordersEntityList = orderRepository.findByUserOder_Id(userId);
            return ordersEntityList.stream().map(orders ->
                            orderMapper.EntityToOrderHistory(orders))
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new  CustomException(e.getMessage());
        }
    }

    @Override
    public OrdersEntity findByOrderIdAndStatus(long orderId, EStatus eStatus) {
        try {
            return orderRepository.findByOrderIdAndStatus(orderId,EStatus.WAITING);
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * lấy ra danh sách lịch sử đơn hàng theo trạng thái đơn hàng, UserId
     * @param userId
     * @param oderStatus
     * @return
     */
    @Override
    public List<OrderHistoryResponse> findOrderByUserIdAndStatus(long userId, String oderStatus) {
        try {
            EStatus eStatus = stringToEnumStatusValue(oderStatus);
            List<OrdersEntity>ordersEntityList = orderRepository.findByUserOder_IdAndStatus(userId,eStatus);
            return ordersEntityList.stream().map(orders ->
                            orderMapper.EntityToOrderHistory(orders))
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new  CustomException(e.getMessage());
        }
    }

    /**
     * Huy don hang dang trong trang thai cho
     * @param userId
     * @param orderId
     * @return
     */
    @Override
    public OrderHistoryResponse cancelOder(long userId, long orderId) {
            OrdersEntity orders = findByOrderIdAndUserOder_Id(orderId,userId);
            if (orders==null){
                throw new CustomException("Order Not Exist");
            }
            if (orders.getStatus().equals(EStatus.WAITING)){
                orders.setStatus(EStatus.CANCEL);
                return orderMapper.EntityToOrderHistory(orderRepository.save(orders));
            }else {
                throw new CustomException("Oder can't Cancel By Success");
            }
    }

    @Override
    public OrdersEntity findByOrderIdAndUserOder_Id(long orderId, long userId) {
           return orderRepository.findByOrderIdAndUserOder_Id(orderId,userId);
    }

    @Override
    public List<OrderByStatusResponse> findByStatus(String oderStatus) {
        EStatus eStatus = stringToEnumStatusValue(oderStatus);
        List<OrdersEntity> list = orderRepository.findByStatus(eStatus);
        return list.stream().map(orders ->
                orderMapper.EntityToOrderByStatus(orders))
                .collect(Collectors.toList());
    }

    /**
     * Chi tiet don hang theo ma OrderId
     * @param orderId
     * @return
     */
    @Override
    public OrderViewResponse viewOrder(long orderId) {
        OrderResponse orderResponse = findByIdRes(orderId);
        List<OrderDetailResponse> list = orderDetailService.findByIdRes(orderId);
        OrderViewResponse viewResponse = new OrderViewResponse();
        viewResponse.setOrder(orderResponse);
        viewResponse.setDetailList(list);
        return viewResponse;
    }

    /**
     * Cap nhat trang thai cua don hang Order
     * @param orderId
     * @param orderStatusRe
     * @return
     */
    @Override
    public OrderHistoryResponse updateStatusOrder(long orderId, String orderStatusRe) {
        try {
            OrdersEntity orders = findById(orderId);
            EStatus eStatus = stringToEnumStatusValue(orderStatusRe);
            orders.setStatus(eStatus);
            return orderMapper.EntityToOrderHistory(orderRepository.save(orders));
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }


    public EStatus stringToEnumStatusValue(String orderStatus){
        if (orderStatus.equalsIgnoreCase("WAITING")){
            return EStatus.WAITING;
        }
        if (orderStatus.equalsIgnoreCase("CONFIRM")){
            return EStatus.CONFIRM;
        }
        if (orderStatus.equalsIgnoreCase("DELIVERY")){
            return EStatus.DELIVERY;
        }
        if (orderStatus.equalsIgnoreCase("SUCCESS")){
            return EStatus.SUCCESS;
        }
        if (orderStatus.equalsIgnoreCase("CANCEL")){
            return EStatus.CANCEL;
        }
        if (orderStatus.equalsIgnoreCase("DENIED")){
            return EStatus.DENIED;
        }else {
            throw  new CustomException("Status not Exist");
        }

    }

    @Override
    public OrdersEntity findById(long oderId) {
            Optional<OrdersEntity> orders = orderRepository.findById(oderId);
            if (orders.isPresent()){
                return orders.get();
            }else {
                throw new CustomException("Order not Exist");
            }

    }

    @Override
    public OrderResponse findByIdRes(long oderId) {
        return orderMapper.EntityToResponse(findById(oderId));
    }

    @Override
    public OrdersEntity findBySerialNumber(String serialNumber) {
        try {
            return orderRepository.findBySerialNumber(serialNumber);
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }

    }


}
