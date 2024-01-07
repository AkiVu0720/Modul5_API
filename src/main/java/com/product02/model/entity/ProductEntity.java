package com.product02.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long productId;
    @Column(name = "sku",columnDefinition = "varchar(100)", unique = true)
    private String sku;
    @Column(name = "product_name", columnDefinition = "varchar(100)", nullable = false, unique = true)
    private String productName;
    @Column(name = "description",columnDefinition = "text")
    private String description;
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;
    @Column(name = "stock_quantity")
    private int quantity;
    private String image;
    @Column(name = "status")
    private boolean status;
    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date created;
    @Column(name = "updated_at")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date updated;


    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private CategoryEntity category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ShoppingCardEntity>shopCart;

    @OneToMany(mappedBy = "productWish",cascade = CascadeType.ALL)
    private List<WishListEntity> wishLists;

    @OneToMany(mappedBy = "productEntity",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderDetailEntity>orderDetailEntityList;


}
