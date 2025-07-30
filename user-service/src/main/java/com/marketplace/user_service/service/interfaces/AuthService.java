package com.marketplace.user_service.service.interfaces;

import com.marketplace.user_service.model.request.ChangePasswordRequest;
import com.marketplace.user_service.model.request.LoginRequest;
import com.marketplace.user_service.model.response.ChangePasswordResponse;
import com.marketplace.user_service.model.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    String changePassword(ChangePasswordRequest request);

}
