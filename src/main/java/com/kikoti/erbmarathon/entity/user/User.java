package com.kikoti.erbmarathon.entity.user;

import com.kikoti.erbmarathon.entity.BaseModel;
import com.kikoti.erbmarathon.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user-profile")
public class User extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String rememberCode;
    private Integer archive;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

}

