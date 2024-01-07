package com.product02.payload.response;

import com.product02.model.entity.EStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderByStatusResponse {
    private long orderId;
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    private EStatus status;
}
