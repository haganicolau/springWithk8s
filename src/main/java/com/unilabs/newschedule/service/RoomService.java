package com.unilabs.newschedule.service;

import com.unilabs.newschedule.dto.RoomDto;
import com.unilabs.newschedule.dto.factorydto.FactoryRoomDto;
import com.unilabs.newschedule.exception.DateTimeConflictException;
import com.unilabs.newschedule.exception.ObjectAlreadyExistException;
import com.unilabs.newschedule.model.Availability;
import com.unilabs.newschedule.model.Room;
import com.unilabs.newschedule.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Room services class
 */
@Service
public class RoomService {

    RoomRepository roomRepository;

    FactoryRoomDto<RoomDto, Room> factoryRoomDto;

    @Autowired
    public RoomService(RoomRepository roomRepository, FactoryRoomDto<RoomDto, Room> factoryRoomDto) {
        this.roomRepository = roomRepository;
        this.factoryRoomDto = factoryRoomDto;
    }

    public boolean isTimeAvailable(String code, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd) {
        long collisionCount = this.roomRepository.countCollisions(dateTimeStart, dateTimeEnd, code);
        return collisionCount == 0;
    }

    /**
     *Method containing the business rules for room registration.
     * 1- Check if the code is not already registered.
     * 2- Convert the roomDto received from the request into the Room entity.
     * 3- Check in the list of availabilities for any conflicts; if conflicting dates are found, it will raise an exception.
     * 4- Save the room in the database.
     *
     * @param roomDto roomDto
     */
    public void insertRoom(RoomDto roomDto) {

        if (this.getByCode(roomDto.getCode()) != null) {
            throw new ObjectAlreadyExistException("Code already exists");
        }

        Room room = this.factoryRoomDto.buildFromDto(roomDto);

        for (Availability availability : room.getAvailabilities()) {
            boolean isAvailable = isTimeAvailable(roomDto.getCode(),
                    availability.getDateTimeStart(), availability.getDateTimeEnd());

            if (!isAvailable) {
                throw new DateTimeConflictException("Unable to create availabilities, conflict dates");
            }
        }

        this.roomRepository.save(room);
    }

    /**
     * Get Room by code
     * @param code code
     * @return Room
     */
    public Room getByCode(String code) {
        Optional<Room> optionalRoom = roomRepository.findByCode(code);

        return optionalRoom.orElse(null);
    }

    /**
     * Retrieve a list of available rooms using a date for consultation and the room code.
     * @param code code to find
     * @param scheduleTime time to check availability
     * @return list of rooms
     */
    public List<Room> getByCodeAndScheduleTime(String code, LocalDateTime scheduleTime) {
        return this.roomRepository.findExamByCodeAndDateTime(code,scheduleTime);
    }
}
