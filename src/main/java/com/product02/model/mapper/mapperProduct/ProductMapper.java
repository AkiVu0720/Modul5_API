package com.product02.model.mapper.mapperProduct;

import com.product02.model.entity.CategoryEntity;
import com.product02.model.entity.ProductEntity;
import com.product02.model.mapper.IMapperGeneric;
import com.product02.payload.requet.ProductRequest;
import com.product02.payload.response.ProductPermitResponse;
import com.product02.payload.response.ProductResponse;
import com.product02.payload.response.ProductResultResponse;
import com.product02.payload.response.ProductStatusTrueResponse;
import com.product02.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
@Component
public class ProductMapper implements IMapperGeneric<ProductEntity, ProductRequest, ProductResponse> {
    @Autowired
    private CategoryService categoryService;

    @Override
    public ProductEntity requestToEntity(ProductRequest productRequest) {
        CategoryEntity category =categoryService.findEntityById(productRequest.getCategoryId());
        return ProductEntity.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .unitPrice(productRequest.getUnitPrice())
                .quantity(productRequest.getQuantity())
                .image(productRequest.getImage())
                .category(category)
                .created(new Date())
                .status(true)
                .build();
    }

    @Override
    public ProductResponse EntityToResponse(ProductEntity productEntity) {
        return ProductResponse.builder()
                .productId(productEntity.getProductId())
                .categoryId(productEntity.getCategory().getCategoryId())
                .sku(productEntity.getSku())
                .productName(productEntity.getProductName())
                .description(productEntity.getDescription())
                .unitPrice(productEntity.getUnitPrice())
                .quantity(productEntity.getQuantity())
                .status(productEntity.isStatus())
                .created(productEntity.getCreated())
                .updated(productEntity.getUpdated())
                .build();
    }
    public ProductPermitResponse EntityToResponsePermit(ProductEntity productEntity) {
        return ProductPermitResponse.builder()
                .productId(productEntity.getProductId())
                .categoryName(productEntity.getCategory().getCategoryName())
                .sku(productEntity.getSku())
                .productName(productEntity.getProductName())
                .description(productEntity.getDescription())
                .unitPrice(productEntity.getUnitPrice())
                .quantity(productEntity.getQuantity())
                .status(productEntity.isStatus())
                .build();
    }
    public ProductStatusTrueResponse EntityToResponseStatusTrue(ProductEntity productEntity) {
        return ProductStatusTrueResponse.builder()
                .productId(productEntity.getProductId())
                .sku(productEntity.getSku())
                .productName(productEntity.getProductName())
                .description(productEntity.getDescription())
                .status(productEntity.isStatus())
                .build();
    }
    public ProductResultResponse EntityToProductResultResponse(ProductEntity productEntity, long total){
        return ProductResultResponse.builder()
                .totalNumber(total)
                .productId(productEntity.getProductId())
                .categoryId(productEntity.getCategory().getCategoryId())
                .sku(productEntity.getSku())
                .productName(productEntity.getProductName())
                .description(productEntity.getDescription())
                .unitPrice(productEntity.getUnitPrice())
                .quantity(productEntity.getQuantity())
                .status(productEntity.isStatus())
                .build();
    }
}
