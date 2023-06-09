package com.noron.core.data.request.core;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserLoginRequest {
    private String userAccount;
    private String password;
}
