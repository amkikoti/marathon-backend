package com.kikoti.erbmarathon.repository;

import com.kikoti.erbmarathon.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, String> {
    JwtToken findTokenByData(String data);
}

