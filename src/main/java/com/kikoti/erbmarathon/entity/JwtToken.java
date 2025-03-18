package com.kikoti.erbmarathon.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_token")
public class JwtToken {

    @Id
    @GeneratedValue
    private Integer id;
    private String data;
    private boolean isRevoked;
    private Long createdAt;
    private Long updatedAt;

    public boolean isRevoked() {
        return isRevoked;
    }

    public void setRevoked(boolean revoked) {
        isRevoked = revoked;
    }

    public JwtToken(String data) {
        this.data = data;
        this.isRevoked = false;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

}
