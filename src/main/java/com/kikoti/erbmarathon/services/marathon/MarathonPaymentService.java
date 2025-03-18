package com.kikoti.erbmarathon.services.marathon;

import com.kikoti.erbmarathon.dtos.MarathonPaymentDto;
import com.kikoti.erbmarathon.entity.MarathonPayment;
import com.kikoti.erbmarathon.utils.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface MarathonPaymentService {
    ResponseEntity<ResponseWrapper<MarathonPayment>> saveMarathon(@Valid MarathonPaymentDto request);
    ResponseEntity<ResponseWrapper<Page<MarathonPayment>>> getAllMarathons(int page, int size);
    ResponseEntity<ResponseWrapper<MarathonPayment>> getMarathonById(Long id);
    ResponseEntity<ResponseWrapper<Void>> deleteMarathon(Long id);
}
