package com.product02.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartResponse {
    private int cartId;
    private String productName;
    private int quantity;
    private  double price;
}
