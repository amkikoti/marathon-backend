package com.kikoti.erbmarathon.services.participant;


import com.kikoti.erbmarathon.dtos.MarathonRegistrationDto;
import com.kikoti.erbmarathon.entity.MarathonRegistration;

public interface MarathonRegistrationService {
    MarathonRegistration registerForMarathon(MarathonRegistrationDto registrationDto);
    MarathonRegistration getCurrentUserRegistration(Long marathonId);
}