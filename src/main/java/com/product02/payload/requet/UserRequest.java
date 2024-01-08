package com.product02.payload.requet;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String fullName;
    @Email
    private String email;
    @Length(min = 9,max = 15)
    private String phone;
    private String address;


}
