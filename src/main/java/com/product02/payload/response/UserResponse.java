package com.product02.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.product02.model.entity.RolesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private long id;
    private String fullName;
    private String userName;
    private String email;
    private String avatar;
    private String phone;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date updated;
    private String address;
    private boolean status;
    private Set<RolesEntity>listRole;
}
