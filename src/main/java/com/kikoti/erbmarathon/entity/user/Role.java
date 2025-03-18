package com.kikoti.erbmarathon.entity.user;

import jakarta.persistence.*;
import com.kikoti.erbmarathon.enums.UserRole;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user-role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private UserRole role;
    private String description;
    private Integer archive;


    public Role(UserRole role, String description) {
        this.role = role;
        this.description = description;
        this.archive = 0;
    }


}
