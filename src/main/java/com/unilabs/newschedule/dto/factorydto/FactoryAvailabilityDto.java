package com.unilabs.newschedule.dto.factorydto;

import com.unilabs.newschedule.dto.AvailabilityDto;
import com.unilabs.newschedule.model.Availability;
import com.unilabs.newschedule.model.AvailabilityStatus;
import com.unilabs.newschedule.model.DayOfWeek;
import com.unilabs.newschedule.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A factory that performs the conversion of the Availability entity to AvailabilityDto and AvailabilityDto
 * to Availability
 * @param <T> AvailabilityDto
 * @param <F> Availability
 */
@Component
public class FactoryAvailabilityDto<T, F> implements IFactoryDto<AvailabilityDto, Availability> {


    private final DateUtil dateUtil;

    @Autowired
    public FactoryAvailabilityDto(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    /**
     * Receives an Availability as a parameter, converts it, and returns an AvailabilityDto.
     * @param entity Availability
     * @return AvailabilityDto
     */
    @Override
    public AvailabilityDto buildFromEntity(Availability entity) {
        AvailabilityDto dto = new AvailabilityDto();
        dto.setDateTimeStart(dateUtil.localDateTimeToString(entity.getDateTimeStart()));
        dto.setDateTimeEnd(dateUtil.localDateTimeToString(entity.getDateTimeEnd()));
        dto.setStatus(entity.getStatus().name());
        dto.setDaysOfWeek(convertDayOfWeekDtoList(entity.getWeekdays()));
        return dto;
    }

    /**
     * Receives a list of DayOfWeek as a parameter, converts it, and returns a list of strings
     * @param weekdays list of DayOfWeek
     * @return list of strings
     */
    public List<String> convertDayOfWeekDtoList(List<DayOfWeek> weekdays) {
        if (weekdays == null || weekdays.isEmpty()) {
            return null;
        }

        return weekdays.stream()
                .map(DayOfWeek::name)
                .collect(Collectors.toList());
    }

    /**
     * Receives an AvailabilityDto as a parameter, converts it, and returns an Availability.
     * @param dto AvailabilityDto
     * @return Availability
     */
    @Override
    public Availability buildFromDto(AvailabilityDto dto) {
        Availability availability = new Availability();
        availability.setDateTimeStart(dateUtil.stringToLocalDateTime(dto.getDateTimeStart()));
        availability.setDateTimeEnd(dateUtil.stringToLocalDateTime(dto.getDateTimeEnd()));
        availability.setWeekdays(convertDayOfWeekList(dto.getDaysOfWeek()));
        availability.setStatus(AvailabilityStatus.valueOf(dto.getStatus()));
        return availability;
    }

    /**
     * Receives a list of strings as a parameter, converts it, and returns a list of DayOfWeek
     * @param weekdays list of strings
     * @return list of DayOfWeek
     */
    public List<DayOfWeek> convertDayOfWeekList(List<String> weekdays) {
        if (weekdays == null || weekdays.isEmpty()) {
            return null;
        }

        return weekdays.stream()
                .map(this::mapStringToDayOfWeek)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * return enum DayOfWeek
     * @param dayOfWeekString string day of week
     * @return
     */
    private DayOfWeek mapStringToDayOfWeek(String dayOfWeekString) {
        return DayOfWeek.valueOf(dayOfWeekString);
    }

}