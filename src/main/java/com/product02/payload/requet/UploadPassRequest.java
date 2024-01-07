package com.product02.payload.requet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadPassRequest {
    private String oldPass;
    private String newPass;
    private String confirmPass;
}
