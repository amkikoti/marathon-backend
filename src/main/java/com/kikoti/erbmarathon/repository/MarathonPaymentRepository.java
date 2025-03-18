package com.kikoti.erbmarathon.repository;

import com.kikoti.erbmarathon.entity.MarathonPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarathonPaymentRepository extends JpaRepository<MarathonPayment, Long> {
    Page<MarathonPayment> findAll(Pageable pageable);
}
