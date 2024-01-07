package com.product02.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Table(name = "category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;
    @Column(name = "category_name", columnDefinition = "varchar(100)", nullable = false)
    private String categoryName;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "category")
    private List<ProductEntity>productList;
}
