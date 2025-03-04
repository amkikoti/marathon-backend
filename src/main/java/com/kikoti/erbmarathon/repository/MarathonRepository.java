package com.kikoti.erbmarathon.repository;

import com.kikoti.erbmarathon.entity.Marathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarathonRepository extends JpaRepository<Marathon, Long> {
}
