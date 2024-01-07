package com.product02.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BestProductsInMonthRes {
    private long productId;
    private String productName;
    private long quantity;
}
