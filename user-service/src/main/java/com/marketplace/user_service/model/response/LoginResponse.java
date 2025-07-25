package com.marketplace.user_service.model.response;

import com.marketplace.user_service.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
