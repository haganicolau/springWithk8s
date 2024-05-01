package com.unilabs.newschedule.repository;

import com.unilabs.newschedule.model.Room;
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
 data access operations related to the Room class.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByCode(String code);

    /**
     * Count the number of Availability records already registered between the dateTimeStart and dateTimeEnd dates
     * and with the specified code. It checks for a schedule conflict with that code.

     * @param dateTimeStart date to Start
     * @param dateTimeEnd date to end
     * @param roomCode code
     * @return counted availabilities
     */
    @Query("SELECT COUNT(a) FROM Availability a " +
            "WHERE :dateTimeStart < a.dateTimeEnd AND :dateTimeEnd > a.dateTimeStart " +
            "AND a.room.code = :roomCode")
    long countCollisions(
            @Param("dateTimeStart") LocalDateTime dateTimeStart,
            @Param("dateTimeEnd") LocalDateTime dateTimeEnd,
            @Param("roomCode") String roomCode
    );

    /**
     * Retrieve a list of available exams using a date for consultation and the room code.
     * @param code room code
     * @param dateTimeToCheck time to schedule
     * @return rooms list
     */
    @Query("SELECT r FROM Room r " +
            "JOIN r.availabilities a " +
            "WHERE r.code = :code " +
            "AND a.status = 'AVAILABLE' " +
            "AND :dateTimeToCheck BETWEEN a.dateTimeStart AND a.dateTimeEnd")
    List<Room> findExamByCodeAndDateTime(
            @Param("code") String code,
            @Param("dateTimeToCheck") LocalDateTime dateTimeToCheck
    );

}
