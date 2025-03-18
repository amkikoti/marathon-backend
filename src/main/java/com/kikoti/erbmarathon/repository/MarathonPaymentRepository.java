package com.kikoti.erbmarathon.repository;

import com.kikoti.erbmarathon.entity.MarathonPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MarathonPaymentRepository extends JpaRepository<MarathonPayment, Long> {
    Page<MarathonPayment> findAll(Pageable pageable);
}
