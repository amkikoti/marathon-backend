package com.kikoti.erbmarathon.repository;

import com.kikoti.erbmarathon.entity.Marathon;
import com.kikoti.erbmarathon.entity.MarathonRegistration;
import com.kikoti.erbmarathon.entity.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MarathonRegistrationRepository extends JpaRepository<MarathonRegistration, Long> {

    boolean existsByMarathonAndUser(Marathon marathon, UserPrincipal user);

    Long countByMarathon(Marathon marathon);

    Optional<MarathonRegistration> findByMarathonAndUser(Marathon marathon, UserPrincipal user);
}