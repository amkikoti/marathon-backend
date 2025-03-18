package com.kikoti.erbmarathon.dtos;

import com.kikoti.erbmarathon.enums.EntryType;
import com.kikoti.erbmarathon.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarathonRegistrationDto {

    private Long id;
    @NotNull(message = "Date is required")
    private String registrationDate;
    //    @NotNull(message = "Date is required")
    @NotNull(message = "T-shirt size is required")
    private String registrationSize;
    @NotNull(message = "Number is required")
    private String registrationPhoneNumber;
    @NotNull(message = "Id Number is required")
    private String registrationIdNumber;
    //    @NotNull(message = "Passport is required")
    private String registrationPassportNumber;
    @NotNull(message = "Emergency contact is required")
    private String registrationEmergencyContact;
    @NotNull(message = "Emergency number is required")
    private String registrationEmergencyNumber;
    //    @NotNull(message = "Address is required")
    private String registrationAddress;
    @NotNull(message = "Country is required")
    private String registrationCountry;
    @NotNull(message = "Age is required")
    private Long registrationAge;
    @NotNull(message = "Gender is required")
    private Gender registrationGender;
    @NotNull(message = "Entry type is required")
    private EntryType registrationEntry;
    //    @NotNull(message = "Date is required")
    private Long registeredUser;
    @NotNull(message = "Marathon is required")
    private Long registrationMarathon;
    @NotNull(message = "Category is required")
    private Long registrationCategory;
}
