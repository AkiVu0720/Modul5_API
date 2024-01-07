package com.product02.repository;

import com.product02.model.entity.OrderDetailEntity;
import com.product02.model.entity.OrderDetailPk;
import com.product02.model.entity.OrdersEntity;
import com.product02.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, OrderDetailPk>{
    @Query(value ="select sum(od.unit_price) from order_detail od where od.order_id = ?",nativeQuery = true)
    BigDecimal sumTotalByOderId(long orderId);
    List<OrderDetailEntity> findByOrdersEntity_OrderId(long orderId);
}
