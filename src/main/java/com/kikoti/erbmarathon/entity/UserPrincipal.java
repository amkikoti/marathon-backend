package com.kikoti.erbmarathon.entity;

import com.kikoti.erbmarathon.entity.user.User;
import com.kikoti.erbmarathon.enums.UserStatus;
import com.kikoti.erbmarathon.exception.ApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import static com.kikoti.erbmarathon.enums.UserStatus.ACTIVE;
import static com.kikoti.erbmarathon.enums.UserStatus.NEEDPASSWORDCHANGE;

public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        UserStatus status = user.getStatus();
        if (status.equals(ACTIVE)  || status.equals(NEEDPASSWORDCHANGE)) {
            return true;
        } else {
            throw new ApiRequestException("User is not active", HttpStatus.UNAUTHORIZED);
        }
    }
}
