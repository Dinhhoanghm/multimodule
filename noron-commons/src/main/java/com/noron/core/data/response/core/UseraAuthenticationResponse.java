package com.noron.core.data.response.core;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UseraAuthenticationResponse {
    private String token;
}
