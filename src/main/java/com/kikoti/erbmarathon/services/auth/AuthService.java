package com.kikoti.erbmarathon.services.auth;

import com.kikoti.erbmarathon.dtos.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> register(SignupDto request);
    ResponseEntity<?> authenticate(SigninDto request);
    ResponseEntity<?> resetPassword(ResetPasswordDto request);
    ResponseEntity<?> confirmResetPassword(ConfirmResetPasswordDto request);
    ResponseEntity<?> resetUserPassword(ResetPasswordDto request);
    ResponseEntity<?> changeUserStatus(ChangeStatusDto request);
    ResponseEntity<?> changePassword(ChangePasswordDto request, String auth);
}