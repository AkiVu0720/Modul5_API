package com.product02.payload.requet;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotNull(message = "Name not null")
    @NotEmpty(message = "Name not empty")
    @Length(max = 100)
    private String fullName;
    @NotNull(message = "userName not null")
    @NotEmpty(message = "userName not empty")
    @Length(min = 6,max = 100,message = "Min:6 - Max:100")
    private String userName;
    @NotNull(message = "Password not null")
    @NotEmpty(message = "Password not empty")
    private String password;
    @Email(message = "Email invalid format" )
    private String email;
    @Pattern(regexp = "/^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$/mg")
    private String phone;
    @NotNull(message = "Address not null")
    @NotEmpty(message = "Address not empty")
    private String address;
}
