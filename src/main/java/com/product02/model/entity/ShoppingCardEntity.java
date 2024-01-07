package com.product02.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shopping_cart")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCardEntity {
    @Id
    @Column(name = "shopping_cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;
    @Column(name = "order_quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
