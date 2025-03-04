package com.kikoti.erbmarathon.services.auth;

import com.kikoti.erbmarathon.dtos.SignupRequest;
import com.kikoti.erbmarathon.dtos.UserDto;
import com.kikoti.erbmarathon.entity.Users;
import com.kikoti.erbmarathon.enums.UserRole;
import com.kikoti.erbmarathon.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount(){
        Users adminAccount = userRepository.findByUserRole(UserRole.ADMIN_ROLE);
        if (adminAccount == null){
            Users newAdminAccount = new Users();
            newAdminAccount.setName("Admin");
            newAdminAccount.setUserRole(UserRole.ADMIN_ROLE);
            newAdminAccount.setEmail("admin@test.com");
            newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(newAdminAccount);
        }
    }

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
        Users user = new Users();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.USER_ROLE);
        Users createdCustomer = userRepository.save(user);

        UserDto cretaedUserDto = new UserDto();
        cretaedUserDto.setId(createdCustomer.getId());
        cretaedUserDto.setName(createdCustomer.getName());
        cretaedUserDto.setEmail(createdCustomer.getEmail());
        cretaedUserDto.setUserRole(createdCustomer.getUserRole());
        return cretaedUserDto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

}
