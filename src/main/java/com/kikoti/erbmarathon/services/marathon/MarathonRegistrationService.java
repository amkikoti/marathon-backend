package com.kikoti.erbmarathon.services.marathon;


import com.kikoti.erbmarathon.dtos.MarathonRegistrationDto;
import com.kikoti.erbmarathon.entity.MarathonRegistration;
import com.kikoti.erbmarathon.utils.ResponseWrapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface MarathonRegistrationService {
    ResponseEntity<ResponseWrapper<MarathonRegistration>> saveMarathonRegistration(MarathonRegistrationDto request);
    ResponseEntity<ResponseWrapper<Page<MarathonRegistration>>> getAllRegistrations(int page, int size);
    ResponseEntity<ResponseWrapper<MarathonRegistration>> getRegistrationById(Long id);
    ResponseEntity<ResponseWrapper<Void>> deleteRegistration(Long id);
}