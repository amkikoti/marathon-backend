package com.kikoti.erbmarathon.repository;

import com.kikoti.erbmarathon.entity.Marathon;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarathonRepository extends JpaRepository<Marathon, Long> {
    @NotNull
    Page<Marathon> findAll(@NotNull Pageable pageable);
}
