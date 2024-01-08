package com.product02.repository;

import com.product02.model.entity.EStatus;
import com.product02.model.entity.ProductEntity;
import com.product02.payload.response.BestProductsInMonthRes;
import com.product02.payload.response.BestSellerResponse;
import com.product02.payload.response.ProductResponse;
import com.product02.payload.response.WishListFeatured;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.Style;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    boolean existsByProductName(String productName);
    boolean existsBySku(String sku);
    Page<ProductEntity>findByStatusIsTrue(Pageable pageable);
    List<ProductEntity> findByProductNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String productName, String description);
    List<ProductEntity> findByProductNameContainingIgnoreCase( String description);
    List<ProductEntity> findByDescriptionContainingIgnoreCase( String description);

    List<ProductEntity>findByCategory_CategoryIdAndStatusIsTrue(long categoryId);
    @Query("select p from ProductEntity p ORDER BY p.created desc limit 5")
    List<ProductEntity>findTop5ByStatusIsTrueOrderByCreatedDesc11();

    // đoạn new com...  là gọi đến tạo mới  đối tượng BestSellerResponse(). Tham số bên trong tương ứng với 2 value trong class.
    //List<BestSellerResponse>: dữ liệu trả ra là list đối tượng BestSellerResponse. Hứng các giá trị của câu query trả ra.
    @Query("SELECT new com.product02.payload.response.BestSellerResponse(ode.productEntity.productId, sum(ode.quantity)) " +
            "from OrderDetailEntity as ode join OrdersEntity as o on o.orderId = ode.ordersEntity.orderId " +
            "join ProductEntity pr on pr.productId=ode.productEntity.productId where o.status=?1 and pr.status =true " +
            "group by ode.productEntity.productId " +
            "order by sum(ode.quantity) DESC limit 10")
    List<BestSellerResponse> findByStatusSuccess(EStatus eStatus);
    @Query("select new com.product02.payload.response.BestProductsInMonthRes(pr.productId,pr.productName,sum(od.quantity))from OrderDetailEntity od join OrdersEntity o on o.orderId=od.ordersEntity.orderId join" +
            " ProductEntity pr on pr.productId=od.productEntity.productId where month (o.orderAt)=month (current_date ) and o.status like 'SUCCESS'GROUP BY pr.productId order by sum(od.quantity) desc limit 10")
    List<BestProductsInMonthRes> bestProductsInMonthRes();
    @Query("select sum(o.price) from OrdersEntity o where o.status like 'SUCCESS' AND o.created between ?1 and ?2 ")
    double revenueByTime(Date from, Date to);


}
