package com.kikoti.erbmarathon.services.marathon;

import com.kikoti.erbmarathon.dtos.MarathonPaymentDto;
import com.kikoti.erbmarathon.entity.MarathonPayment;
import com.kikoti.erbmarathon.repository.MarathonPaymentRepository;
import com.kikoti.erbmarathon.utils.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarathonPaymentServiceImpl implements MarathonPaymentService {

    private final MarathonPaymentRepository marathonPaymentRepository;

    @Override
    public ResponseEntity<ResponseWrapper<MarathonPayment>> saveMarathon(@Valid MarathonPaymentDto request) {
        MarathonPayment payment = new MarathonPayment();
        payment.setPaymentDate(request.getPaymentDate());
        payment.setPaymentTime(request.getPaymentTime());
        payment.setPaymentAmount(request.getPaymentAmount());
        payment.setPaymentStatus(request.getPaymentStatus());
        payment.setPaymentReference(request.getPaymentReference());
        payment.setPaymentChannel(request.getPaymentChannel());
        payment.setPaymentChannelName(request.getPaymentChannelName());
        if (request.getId() != null) {
            Optional<MarathonPayment> data = marathonPaymentRepository.findById(request.getId());
            if (data.isPresent()) {
                payment.setId(request.getId());
            } else {
                ResponseWrapper<MarathonPayment> response = new ResponseWrapper<>(null, "Marathon Payment not found", false);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }
        MarathonPayment savedMarathon = marathonPaymentRepository.save(payment);
        ResponseWrapper<MarathonPayment> response = new ResponseWrapper<>(savedMarathon, "Marathon saved successfully", true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseWrapper<Page<MarathonPayment>>> getAllMarathons(int page, int size) {
        Page<MarathonPayment> marathons = marathonPaymentRepository.findAll(PageRequest.of(page, size));
        ResponseWrapper<Page<MarathonPayment>> response = new ResponseWrapper<>(marathons, "Marathons fetched successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseWrapper<MarathonPayment>> getMarathonById(Long id) {
        Optional<MarathonPayment> marathon = marathonPaymentRepository.findById(id);
        if (marathon.isPresent()) {
            ResponseWrapper<MarathonPayment> response = new ResponseWrapper<>(marathon.get(), "Marathon found", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<MarathonPayment> response = new ResponseWrapper<>(null, "Marathon not found", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ResponseWrapper<Void>> deleteMarathon(Long id) {
        if (marathonPaymentRepository.existsById(id)) {
            marathonPaymentRepository.deleteById(id);
            ResponseWrapper<Void> response = new ResponseWrapper<>(null, "Marathon deleted successfully", true);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseWrapper<Void> response = new ResponseWrapper<>(null, "Marathon not found", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}

