package com.kikoti.erbmarathon.services.marathon;
import com.kikoti.erbmarathon.dtos.MarathonRegistrationDto;
import com.kikoti.erbmarathon.entity.Marathon;
import com.kikoti.erbmarathon.entity.MarathonCategory;
import com.kikoti.erbmarathon.entity.MarathonPayment;
import com.kikoti.erbmarathon.entity.MarathonRegistration;
import com.kikoti.erbmarathon.entity.user.User;
import com.kikoti.erbmarathon.repository.*;
import com.kikoti.erbmarathon.utils.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.kikoti.erbmarathon.enums.PaymentStatus;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarathonRegistrationServiceImpl implements MarathonRegistrationService {

    private final MarathonRegistrationRepository marathonRegistrationRepository;
    private final MarathonRepository marathonRepository;
    private final MarathonCategoryRepository marathonCategoryRepository;
    private final MarathonPaymentRepository marathonPaymentRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseWrapper<MarathonRegistration>> saveMarathonRegistration(MarathonRegistrationDto request) {
        Long userId = request.getRegisteredUser();
        Long marathonId = request.getRegistrationMarathon();
        Long categoryId = request.getRegistrationCategory();
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            ResponseWrapper<MarathonRegistration> response = new ResponseWrapper<>(null, "User not valid", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Optional<Marathon> marathon = marathonRepository.findById(marathonId);
        if (marathon.isEmpty()) {
            ResponseWrapper<MarathonRegistration> response = new ResponseWrapper<>(null, "Marathon not valid", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Optional<MarathonCategory> category = marathonCategoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            ResponseWrapper<MarathonRegistration> response = new ResponseWrapper<>(null, "Category not valid", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        MarathonRegistration marathonRegistration = new MarathonRegistration();
        marathonRegistration.setRegistrationDate(request.getRegistrationDate());
        marathonRegistration.setRegistrationSize(request.getRegistrationSize());
        marathonRegistration.setRegistrationPhoneNumber(request.getRegistrationPhoneNumber());
        marathonRegistration.setRegistrationIdNumber(request.getRegistrationIdNumber());
        marathonRegistration.setRegistrationPassportNumber(request.getRegistrationPassportNumber());
        marathonRegistration.setRegistrationEmergencyContact(request.getRegistrationEmergencyContact());
        marathonRegistration.setRegistrationEmergencyNumber(request.getRegistrationEmergencyNumber());
        marathonRegistration.setRegistrationAddress(request.getRegistrationAddress());
        marathonRegistration.setRegistrationCountry(request.getRegistrationCountry());
        marathonRegistration.setRegistrationAge(request.getRegistrationAge());
        marathonRegistration.setRegistrationGender(request.getRegistrationGender());
        marathonRegistration.setRegistrationEntry(request.getRegistrationEntry());
        marathonRegistration.setRegistrationNumber("9480900");
        marathonRegistration.setRegisteredUser(user.get());
        marathonRegistration.setRegistrationMarathon(marathon.get());
        marathonRegistration.setRegistrationCategory(category.get());

        MarathonPayment payment = new MarathonPayment();
        payment.setPaymentDate(null);
        payment.setPaymentTime(null);
        payment.setPaymentAmount(category.get().getCategoryEntry());
        payment.setPaymentStatus(PaymentStatus.INCOMPLETE);
        payment.setPaymentReference(null);
        payment.setPaymentChannel(null);
        payment.setPaymentChannelName(null);

        MarathonPayment savedPayment = marathonPaymentRepository.save(payment);
        marathonRegistration.setRegistrationPayment(savedPayment);
        if (request.getId() != null) {
            Optional<MarathonRegistration> data = marathonRegistrationRepository.findById(request.getId());
            if (data.isPresent()) {
                marathonRegistration.setId(request.getId());
            } else {
                ResponseWrapper<MarathonRegistration> response = new ResponseWrapper<>(null, "Marathon Registration not found", false);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }
        MarathonRegistration savedMarathon = marathonRegistrationRepository.save(marathonRegistration);
        ResponseWrapper<MarathonRegistration> response = new ResponseWrapper<>(savedMarathon, "Marathon saved successfully", true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseWrapper<Page<MarathonRegistration>>> getAllRegistrations(int page, int size) {
        Page<MarathonRegistration> marathons = marathonRegistrationRepository.findAll(PageRequest.of(page, size));
        ResponseWrapper<Page<MarathonRegistration>> response = new ResponseWrapper<>(marathons, "Marathons fetched successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseWrapper<MarathonRegistration>> getRegistrationById(Long id) {
        Optional<MarathonRegistration> marathon = marathonRegistrationRepository.findById(id);
        if (marathon.isPresent()) {
            ResponseWrapper<MarathonRegistration> response = new ResponseWrapper<>(marathon.get(), "Marathon found", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<MarathonRegistration> response = new ResponseWrapper<>(null, "Marathon not found", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ResponseWrapper<Void>> deleteRegistration(Long id) {
        if (marathonRegistrationRepository.existsById(id)) {
            marathonRegistrationRepository.deleteById(id);
            ResponseWrapper<Void> response = new ResponseWrapper<>(null, "Marathon deleted successfully", true);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseWrapper<Void> response = new ResponseWrapper<>(null, "Marathon not found", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
