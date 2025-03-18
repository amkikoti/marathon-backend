package com.kikoti.erbmarathon.controller;

import com.kikoti.erbmarathon.dtos.MarathonRegistrationDto;
import com.kikoti.erbmarathon.entity.MarathonRegistration;
import com.kikoti.erbmarathon.services.marathon.MarathonRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/marathons")
@RequiredArgsConstructor
public class MarathonRegistrationController {

    private final MarathonRegistrationService registrationService;

    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MarathonRegistration> register(@RequestBody MarathonRegistrationDto registrationDto) {
        return new ResponseEntity<>(
                registrationService.registerForMarathon(registrationDto),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{marathonId}/my-registration")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MarathonRegistration> getMyRegistration(@PathVariable Long marathonId) {
        return ResponseEntity.ok(registrationService.getCurrentUserRegistration(marathonId));
    }
}