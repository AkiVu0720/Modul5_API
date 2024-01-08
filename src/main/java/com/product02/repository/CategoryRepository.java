package com.product02.repository;

import com.product02.model.entity.CategoryEntity;
import com.product02.payload.response.BasicResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
        List<CategoryEntity> findAllByStatusIsTrue();
//        @Query("select new com.product02.payload.response.BasicResponse(ct.categoryName,sum (od.price))from CategoryEntity ct left join ProductEntity pr on pr.category.categoryId = ct.categoryId left join OrderDetailEntity od on od.productEntity.productId = pr.productId " +
//                "left join OrdersEntity o on o.orderId=od.ordersEntity.orderId  " +
//                "where  o.status like 'SUCCESS' group by ct.categoryName")

        @Query("select new com.product02.payload.response.BasicResponse(ct.categoryName,sum (o.price))from CategoryEntity ct left join ProductEntity pr on pr.category.categoryId = ct.categoryId left join OrderDetailEntity od on od.productEntity.productId = pr.productId " +
                "left join OrdersEntity o on o.orderId=od.ordersEntity.orderId  " +
                "where  o.status like 'SUCCESS' group by ct.categoryName")
        List<BasicResponse> revenueCategory();


        CategoryEntity findByCategoryName(String name);
        boolean existsByCategoryName(String name);
}
