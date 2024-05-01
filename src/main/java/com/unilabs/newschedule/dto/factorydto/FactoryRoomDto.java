package com.unilabs.newschedule.dto.factorydto;

import com.unilabs.newschedule.dto.AvailabilityDto;
import com.unilabs.newschedule.dto.RoomDto;
import com.unilabs.newschedule.model.Availability;
import com.unilabs.newschedule.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory that performs the conversion of the Room entity to RoomDto and ExamDto
 * to Exam
 * @param <T> RoomDto
 * @param <F> Room
 */
@Component
public class FactoryRoomDto <T, F> implements IFactoryDto<RoomDto, Room> {

    private final FactoryAvailabilityDto<AvailabilityDto, Availability> factoryAvailabilityDto;

    @Autowired
    public FactoryRoomDto(FactoryAvailabilityDto<AvailabilityDto, Availability> factoryAvailabilityDto) {
        this.factoryAvailabilityDto = factoryAvailabilityDto;
    }

    /**
     * Receives a Room as a parameter, converts it, and returns a RoomDto.
     * @param entity Room
     * @return RoomDto
     */
    @Override
    public RoomDto buildFromEntity(Room entity) {
        RoomDto dto = new RoomDto();
        dto.setCode(entity.getCode());
        dto.setDescription(entity.getDescription());
        dto.setAvailabilities(getAvailabilityDtoList(entity.getAvailabilities()));
        return dto;
    }

    /**
     * Receives a RoomDto as a parameter, converts it, and returns a Room.
     * @param dto RoomDto
     * @return Room
     */
    @Override
    public Room buildFromDto(RoomDto dto) {
        Room entity = new Room();
        entity.setCode(dto.getCode());
        entity.setDescription(dto.getDescription());
        entity.setAvailabilities(getAvailabilityList(dto.getAvailabilities(), entity));
        return entity;
    }

    /**
     * That method converts an availabilities list into a list of availabilityDto
     * @param availabilities list of availabilities
     * @return list of availabilityDto
     */
    private List<AvailabilityDto> getAvailabilityDtoList(List<Availability> availabilities) {
        List<AvailabilityDto> list = new ArrayList<>();
        for (Availability availability : availabilities) {
            AvailabilityDto dto = this.factoryAvailabilityDto.buildFromEntity(availability);
            list.add(dto);
        }
        return list;
    }

    /**
     * That method converts an availabilitiesDto list into a list of availabilities
     * @param availabilitiesDtoList list of availabilitiesDto
     * @param room Room
     * @return list of availabilities
     */
    private List<Availability> getAvailabilityList(List<AvailabilityDto> availabilitiesDtoList, Room room) {
        List<Availability> list = new ArrayList<>();
        for (AvailabilityDto dto : availabilitiesDtoList) {
            Availability entity = this.factoryAvailabilityDto.buildFromDto(dto);
            entity.setRoom(room);
            list.add(entity);
        }
        return list;
    }
}
