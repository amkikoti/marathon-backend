package com.kikoti.erbmarathon.controller;

import com.kikoti.erbmarathon.dtos.MarathonRegistrationDto;
import com.kikoti.erbmarathon.entity.MarathonRegistration;
import com.kikoti.erbmarathon.services.marathon.MarathonRegistrationService;
import com.kikoti.erbmarathon.utils.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/marathon-registration")
@RequiredArgsConstructor
public class MarathonRegistrationController {
    private final MarathonRegistrationService marathonRegistrationService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<MarathonRegistration>> createOrUpdateMarathon(@Valid @RequestBody MarathonRegistrationDto request) {
        return marathonRegistrationService.saveMarathonRegistration(request);
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<MarathonRegistration>>> getAllMarathons(
            @RequestParam(defaultValue = "0") int page,    // Default page number is 0
            @RequestParam(defaultValue = "10") int size) {  // Default page size is 10
        return marathonRegistrationService.getAllRegistrations(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<MarathonRegistration>> getMarathonById(@PathVariable Long id) {
        return marathonRegistrationService.getRegistrationById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteMarathon(@PathVariable Long id) {
        return marathonRegistrationService.deleteRegistration(id);
    }
}
