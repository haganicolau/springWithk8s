package com.unilabs.newschedule.repository;

import com.unilabs.newschedule.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 An interface used as part of the data persistence mechanism, enabling the execution of
 data access operations related to the Availability class.
 */
@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}
