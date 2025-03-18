package com.kikoti.erbmarathon.services.user;
import com.kikoti.erbmarathon.dtos.UpdateUserDto;
import com.kikoti.erbmarathon.entity.user.User;
import com.kikoti.erbmarathon.exception.ApiRequestException;
import com.kikoti.erbmarathon.repository.UserRepository;
import com.kikoti.erbmarathon.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kikoti.erbmarathon.enums.UserStatus.ACTIVE;
import static com.kikoti.erbmarathon.enums.UserStatus.DEACTIVATED;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Page<User> listUsers(Pageable pageable, String auth) {
        Page<User> users = userRepository.findAll(pageable);
        System.out.println(users);
        List<User> filteredUsers = users.stream().toList();
        System.out.println(filteredUsers);
        return new PageImpl<>(filteredUsers, pageable, users.getTotalElements());
    }

    public User getUserByToken(String auth) {
        String token = auth.replace("Bearer ", "");
        Map<String, Object> decoded = jwtTokenProvider.decodeToken(token);
        String email = (String) decoded.get("email");
        return userRepository.findByEmail(email).orElseThrow(() ->
                new ApiRequestException("User\twith\temail\t" + email + "\tdoes\tnot\texists", HttpStatus.BAD_REQUEST));
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ApiRequestException("User\twith\tid\t" + userId + "\tdoes\tnot\texists", HttpStatus.BAD_REQUEST));
    }

    public Map<String, Object> deactivateUser(Long userId, String Auth) {
        String token = Auth.replace("Bearer ", "");
        Map<String, Object> decoded = JwtTokenProvider.decodeToken(token);
        String updatedBy = (String) decoded.get("userId");

        User user = userRepository.findByIdAndStatusAndArchive(userId, ACTIVE, 0).orElseThrow(() ->
                new ApiRequestException("user does not exist/account is not active", HttpStatus.BAD_REQUEST));

        user.setStatus(DEACTIVATED);
        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User deactivated successfully");
        return response;
    }

    public Map<String, Object> activateUser(Long userId, String Auth) {
        String token = Auth.replace("Bearer ", "");
        Map<String, Object> decoded = JwtTokenProvider.decodeToken(token);
        String updatedBy = (String) decoded.get("userId");

        User user = userRepository.findByIdAndStatusAndArchive(userId, DEACTIVATED, 0).orElseThrow(() ->
                new ApiRequestException("user does not exist", HttpStatus.BAD_REQUEST));

        user.setStatus(ACTIVE);

        userRepository.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User activated successfully");
        return response;
    }


    @Transactional
    public Map<String, Object> updateUserRole(Long userId, String role, String Auth) {
        String token = Auth.replace("Bearer ", "");
        Map<String, Object> decoded = JwtTokenProvider.decodeToken(token);

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ApiRequestException("User\twith\tid\t" + userId + "\tdoes\tnot\texists", HttpStatus.BAD_REQUEST));
        userRepository.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "role updated success");
        return response;
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> updateUser(UpdateUserDto request, String Auth) {
        String token = Auth.replace("Bearer ", "");
        Map<String, Object> decoded = JwtTokenProvider.decodeToken(token);
        User user = userRepository.findByIdAndStatus(request.getId(), ACTIVE).orElseThrow(
                () -> new ApiRequestException("user not found", HttpStatus.BAD_REQUEST));
        userRepository.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User updated successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
