package com.unilabs.newschedule.repository;

import com.unilabs.newschedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 An interface used as part of the data persistence mechanism, enabling the execution of
 data access operations related to the Schedule class.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
