package com.marketplace.user_service.service.interfaces;

public interface EmailService {
    void sendForgotPasswordEmail(String to, String newPassword);
}
