package org.lucycato.userservice.adapter.in.web.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUserCheckTempPasswordRequest {
    private String email;

    private String tempPassword;
}
