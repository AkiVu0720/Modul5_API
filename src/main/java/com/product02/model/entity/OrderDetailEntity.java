package com.product02.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "order_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(OrderDetailPk.class)
public class OrderDetailEntity {
        @Id
        @ManyToOne
        @JoinColumn(name = "order_id")
        @JsonIgnore
        private OrdersEntity ordersEntity;

        @Id
        @ManyToOne
        @JoinColumn(name = "product_id")
        private ProductEntity productEntity;

    @Column(name = "name", columnDefinition = "varchar(100)")
    private String name;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "order_quantity")
    private int quantity;

}
