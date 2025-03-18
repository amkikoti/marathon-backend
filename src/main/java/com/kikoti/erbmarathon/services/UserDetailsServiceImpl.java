package com.kikoti.erbmarathon.services;

import com.kikoti.erbmarathon.entity.UserPrincipal;
import com.kikoti.erbmarathon.entity.user.User;
import com.kikoti.erbmarathon.exception.ApiRequestException;
import com.kikoti.erbmarathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName){
        User user = userRepository.findByEmail(userName).orElseThrow(
                () ->  new ApiRequestException("Wrong username or password", HttpStatus.FORBIDDEN)
        );
        return new UserPrincipal(user);
    }

}
