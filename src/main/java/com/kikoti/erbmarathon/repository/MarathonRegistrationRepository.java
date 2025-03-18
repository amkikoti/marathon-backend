package com.kikoti.erbmarathon.repository;

import com.kikoti.erbmarathon.entity.MarathonRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MarathonRegistrationRepository extends JpaRepository<MarathonRegistration, Long> {
    Page<MarathonRegistration> findAll(Pageable pageable);
    //Optional<MarathonRegistration> findByRegistrationNumber(String registrationNumber);
}