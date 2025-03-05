package com.kikoti.erbmarathon.services.participant;

import com.kikoti.erbmarathon.dtos.MarathonRegistrationDto;
import com.kikoti.erbmarathon.entity.*;
import com.kikoti.erbmarathon.exception.*;
import com.kikoti.erbmarathon.repository.MarathonRegistrationRepository;
import com.kikoti.erbmarathon.repository.MarathonRepository;
import com.kikoti.erbmarathon.services.jwt.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MarathonRegistrationServiceImpl implements MarathonRegistrationService {

    private final MarathonRegistrationRepository registrationRepository;
    private final MarathonRepository marathonRepository;
    private final UserService userService;

    @Override
    @Transactional
    public MarathonRegistration registerForMarathon(MarathonRegistrationDto registrationDto) {
        Marathon marathon = marathonRepository.findById(registrationDto.getMarathonId())
                .orElseThrow(() -> new MarathonNotFoundException("Marathon not found"));

        if (!marathon.isActive()) {
            throw new RegistrationClosedException("Marathon registration is closed");
        }

        Users currentUser = userService.getCurrentUser();

        if (registrationRepository.existsByMarathonAndUser(marathon, currentUser)) {
            throw new DuplicateRegistrationException("User already registered for this marathon");
        }

        long currentParticipants = registrationRepository.countByMarathon(marathon);
        if (currentParticipants >= marathon.getMaxParticipants()) {
            throw new MarathonFullException("Marathon has reached maximum participants");
        }

        MarathonRegistration registration = new MarathonRegistration();
        registration.setMarathon(marathon);
        registration.setUser(currentUser);
        registration.setEmergencyContact(registrationDto.getEmergencyContact());

        return registrationRepository.save(registration);
    }

    @Override
    public MarathonRegistration getCurrentUserRegistration(Long marathonId) {
        Marathon marathon = marathonRepository.findById(marathonId)
                .orElseThrow(() -> new MarathonNotFoundException("Marathon not found"));

        Users currentUser = userService.getCurrentUser();

        return registrationRepository.findByMarathonAndUser(marathon, currentUser)
                .orElseThrow(() -> new RegistrationNotFoundException("Registration not found"));
    }
}