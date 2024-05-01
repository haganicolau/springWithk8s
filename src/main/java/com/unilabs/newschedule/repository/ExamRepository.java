package com.unilabs.newschedule.repository;

import com.unilabs.newschedule.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 *
 An interface used as part of the data persistence mechanism, enabling the execution of
 data access operations related to the Exam class.
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    Optional<Exam> findByCode(String code);
    Optional<Exam> findById(Long id);

    /**
     * Count the number of Availability records already registered between the dateTimeStart and dateTimeEnd dates
     * and with the specified code. It checks for a schedule conflict with that code.

     * @param dateTimeStart date to Start
     * @param dateTimeEnd date to end
     * @param examCode code
     * @return counted availabilities
     */
    @Query("SELECT COUNT(a) FROM Availability a " +
            "WHERE :dateTimeStart < a.dateTimeEnd AND :dateTimeEnd > a.dateTimeStart " +
            "AND a.exam.code = :examCode")
    long countCollisions(
            @Param("dateTimeStart") LocalDateTime dateTimeStart,
            @Param("dateTimeEnd") LocalDateTime dateTimeEnd,
            @Param("examCode") String examCode
    );

    /**
     * Retrieve a list of available exams using a date for consultation and the exam code.
     * @param code exam code
     * @param dateTimeToCheck time to schedule
     * @return exams list
     */
    @Query("SELECT e FROM Exam e " +
            "JOIN e.availabilities a " +
            "WHERE e.code = :code " +
            "AND a.status = 'AVAILABLE' " +
            "AND :dateTimeToCheck BETWEEN a.dateTimeStart AND a.dateTimeEnd")
    List<Exam> findExamByCodeAndDateTime(
            @Param("code") String code,
            @Param("dateTimeToCheck") LocalDateTime dateTimeToCheck
    );
}
