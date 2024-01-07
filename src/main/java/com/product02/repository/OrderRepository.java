package com.product02.repository;

import com.product02.model.entity.EStatus;
import com.product02.model.entity.OrdersEntity;
import com.product02.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {
    OrdersEntity findBySerialNumber(String uuid);
    List<OrdersEntity> findByUserOder_IdAndStatus(long userId, EStatus eStatus);
    OrdersEntity findByOrderIdAndStatus(long orderId,EStatus eStatus);
    List<OrdersEntity> findByUserOder_Id(long userId);
    OrdersEntity findByOrderIdAndUserOder_Id(long orderId, long userId);
    List<OrdersEntity> findByStatus(EStatus eStatus);


}
