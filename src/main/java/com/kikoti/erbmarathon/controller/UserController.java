package com.kikoti.erbmarathon.controller;

import com.kikoti.erbmarathon.dtos.UpdateUserDto;
import com.kikoti.erbmarathon.entity.user.User;
import com.kikoti.erbmarathon.services.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String auth, @Valid @RequestBody UpdateUserDto updateUserDto) {
        return userService.updateUser(updateUserDto, auth);
    }

    @GetMapping("/list")
    public Page<User> listAllUsers(@RequestHeader("Authorization") String auth, Pageable pageable) {
        return userService.listUsers(pageable, auth);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") int userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/profile")
    public User getUser(@RequestHeader("Authorization") String auth) {
        return userService.getUserByToken(auth);
    }

    @PutMapping("/deactivate/{userId}")
    public ResponseEntity<?> deactivateUser(@RequestHeader("Authorization") String Auth, @PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.deactivateUser(userId, Auth), HttpStatus.CREATED);
    }

    @PutMapping("/activate/{userId}")
    public ResponseEntity<?> activateUser(@RequestHeader("Authorization") String Auth, @PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.activateUser(userId, Auth), HttpStatus.CREATED);
    }

    @PutMapping("/update-role/{userId}")
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String Auth, @PathVariable("userId") Long userId, @RequestBody Map<String, Object> payload
    ) {
        String role = (String) payload.get("role");
        return new ResponseEntity<>(userService.updateUserRole(userId, role, Auth), HttpStatus.CREATED);
    }
}