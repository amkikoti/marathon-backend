package com.kikoti.erbmarathon.controller;

import com.kikoti.erbmarathon.dtos.MarathonPaymentDto;
import com.kikoti.erbmarathon.entity.MarathonPayment;
import com.kikoti.erbmarathon.services.marathon.MarathonPaymentService;
import com.kikoti.erbmarathon.utils.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/marathon-payment")
@RequiredArgsConstructor
public class MarathonPaymentController {

    private final MarathonPaymentService marathonPaymentService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<MarathonPayment>> createOrUpdateMarathon(@Valid @RequestBody MarathonPaymentDto request) {
        return marathonPaymentService.saveMarathon(request);
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<MarathonPayment>>> getAllMarathons(
            @RequestParam(defaultValue = "0") int page,    // Default page number is 0
            @RequestParam(defaultValue = "10") int size) {  // Default page size is 10
        return marathonPaymentService.getAllMarathons(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<MarathonPayment>> getMarathonById(@PathVariable Long id) {
        return marathonPaymentService.getMarathonById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteMarathon(@PathVariable Long id) {
        return marathonPaymentService.deleteMarathon(id);
    }
}

