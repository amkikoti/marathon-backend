package com.kikoti.erbmarathon.services.auth;

import com.kikoti.erbmarathon.dtos.SignupRequest;
import com.kikoti.erbmarathon.dtos.UserDto;

public interface AuthService {

    UserDto createCustomer(SignupRequest signupRequest);

    boolean hasCustomerWithEmail(String email);

}
