package com.kikoti.erbmarathon.controller;
import com.kikoti.erbmarathon.dtos.*;
import com.kikoti.erbmarathon.services.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService service;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @Valid @RequestBody SignupDto request
    ) { return service.register(request); }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(
            @Valid @RequestBody SigninDto request
    ) { return service.authenticate(request);}

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @Valid @RequestBody ResetPasswordDto request
    ) { return service.resetPassword(request);}

    @PostMapping("/confirm-reset-password")
    public ResponseEntity<?> confirmResetPassword(
            @Valid @RequestBody ConfirmResetPasswordDto request
    ) { return service.confirmResetPassword(request);}

    @PostMapping("/reset-user-password")
    public ResponseEntity<?> resetUserPassword(
            @Valid @RequestBody ResetPasswordDto request
    ) { return service.resetUserPassword(request);}

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestHeader("Authorization") String token, @Valid @RequestBody ChangePasswordDto request
    ) { return service.changePassword(request, token);}

    @PutMapping("/change-user-status")
    public ResponseEntity<?> changeStatus(
            @Valid @RequestBody ChangeStatusDto request
    ) { return service.changeUserStatus(request);}
}
