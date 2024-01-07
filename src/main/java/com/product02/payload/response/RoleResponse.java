package com.product02.payload.response;

import com.product02.model.entity.ERoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {
    private long roleId;
    private ERoles name;
}
