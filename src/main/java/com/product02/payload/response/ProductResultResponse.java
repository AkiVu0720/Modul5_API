package com.product02.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResultResponse {
    private long totalNumber;
    private long productId;
    private long categoryId;
    private String sku;
    private String productName;
    private String description;
    private BigDecimal unitPrice;
    private int quantity;
    private boolean status;
    private String image;

}
