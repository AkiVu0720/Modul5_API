package com.product02.service;

import com.product02.model.entity.EStatus;
import com.product02.model.entity.OrdersEntity;
import com.product02.model.entity.ProductEntity;
import com.product02.payload.requet.ProductRequest;
import com.product02.payload.requet.ProductUpdateRequest;
import com.product02.payload.response.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProductService {
    Page<ProductResponse> findPageAll(int page, int size, String nameDirection,String idDirection);
    Page<ProductStatusTrueResponse> ProductStatusTrue(int page, int size, String nameDirection,String idDirection);
    ProductEntity findEntityById(long productId);
    boolean isExistProductName(String productName);
    boolean isExistSku(String sku);
    ProductEntity save(ProductEntity productEntity);
    List<ProductPermitResponse> findByNameAndDescription(String nameSort,String descriptionSort);
    ProductResponse create(ProductRequest productRequest);
    boolean delete(long productId);
    ProductResponse update(long productId, ProductUpdateRequest productRequest);
    ProductResponse findById(long productId);
    ProductPermitResponse findProductPermitById(long productId);
    List<ProductResultResponse> listFeaturedProduct();
    List<ProductPermitResponse> listNewProduct();
    List<ProductResponse> bestSellerProduct();
    List<ProductResultResponse> findByStatusSuccess();
    List<BestProductsInMonthRes> bestProductsInMonthRes();
    RevenueByTimeResponse revenueByTime(Date from, Date to);
    List<ProductPermitResponse> listProductByCategoryId(long categoryId);
}
