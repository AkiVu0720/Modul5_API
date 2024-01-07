package com.product02.payload.requet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {
    private String address;
    @Length(max = 15)
    private String phone;
    @Length(max = 50)
    private String receiveName;
}
