package com.kikoti.erbmarathon.services.jwt;

import com.kikoti.erbmarathon.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDetailsService userDetailsService();

    Users getCurrentUser();
}

