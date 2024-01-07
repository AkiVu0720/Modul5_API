package com.product02.payload.requet;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private long categoryId;
    @NotNull(message = "product Name Not null")
    private String productName;
    private String description;
    @Min(0)
    private BigDecimal unitPrice;
    @Digits(message = "Format: ###.##",integer = 10,fraction = 2)
    private int quantity;
    private String image;
}
