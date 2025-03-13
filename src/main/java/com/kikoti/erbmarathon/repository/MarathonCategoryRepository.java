package com.kikoti.erbmarathon.repository;

import com.kikoti.erbmarathon.entity.MarathonCategory;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarathonCategoryRepository extends JpaRepository<MarathonCategory, Long> {
    @NotNull
    Page<MarathonCategory> findAll(@NotNull Pageable pageable);
    List<MarathonCategory> findByIdIn(List<Long> ids);
}
