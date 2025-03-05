package com.kikoti.erbmarathon.repository;

import com.kikoti.erbmarathon.entity.Marathon;
import com.kikoti.erbmarathon.entity.MarathonRegistration;
import com.kikoti.erbmarathon.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MarathonRegistrationRepository extends JpaRepository<MarathonRegistration, Long> {

    boolean existsByMarathonAndUser(Marathon marathon, Users user);

    Long countByMarathon(Marathon marathon);

    Optional<MarathonRegistration> findByMarathonAndUser(Marathon marathon, Users user);
}