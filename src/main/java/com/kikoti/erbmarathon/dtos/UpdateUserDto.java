package com.kikoti.erbmarathon.dtos;

import com.kikoti.erbmarathon.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String idNumber;
    private String passportNumber;
    private LocalDate birthDate;
    private Long age;
    private String address;
    private String country;
    private String emergencyContact;
    private String emergencyNumber;
    private Gender gender;
}
