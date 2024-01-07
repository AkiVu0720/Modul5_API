package com.product02.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JoinTable(name = "User_Roles",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolesEntity> listRoles = new HashSet<>();

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username",columnDefinition = "varchar(100)", unique = true, nullable = false)
    private String userName;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "fullname",columnDefinition = "varchar(100)")
    private String fullName;
    private String avatar;
    @Column(name = "phone", columnDefinition = "varchar(15)",unique = true)
    private String phone;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date created;

    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date updated;

    @Column(name = "address", unique = true)
    private String address;

    @Column(name = "status",columnDefinition = "bit default 1")
    private boolean status;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ShoppingCardEntity> shopCart;

    @OneToMany(mappedBy = "userWish")
    private List<WishListEntity> wishLists;

    @OneToMany(mappedBy = "userAddr")
    @JsonIgnore
    private List<AddressEntity> addressList;

    @OneToMany(mappedBy = "userOder")
    @JsonIgnore
    private Set<OrdersEntity> ordersEntityList;

}
