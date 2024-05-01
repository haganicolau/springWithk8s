package com.unilabs.newschedule.dto.factorydto;

import com.unilabs.newschedule.dto.ScheduleDto;
import com.unilabs.newschedule.model.Schedule;
import com.unilabs.newschedule.model.ScheduleType;
import com.unilabs.newschedule.util.DateUtil;
import org.springframework.stereotype.Component;

/**
 * A factory that performs the conversion of the Schedule entity to ScheduleDto and ScheduleDto
 * to Schedule
 * @param <T> ScheduleDto
 * @param <F> Schedule
 */
@Component
public class FactoryScheduleDto<T,F> implements IFactoryDto<ScheduleDto, Schedule> {

    private final DateUtil dateUtil;

    public FactoryScheduleDto(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    /**
     * Receives a Schedule as a parameter, converts it, and returns a ScheduleDto.
     * @param entity Schedule
     * @return ScheduleDto
     */
    @Override
    public ScheduleDto buildFromEntity(Schedule entity) {
        ScheduleDto dto = new ScheduleDto();
        dto.setType(entity.getScheduleType().name());
        dto.setDateTimeSchedule(dateUtil.localDateTimeToString(entity.getScheduleTime()));
        dto.setUtentePatient(entity.getPatient().getUtente());
        return dto;
    }

    /**
     * Receives a ScheduleDto as a parameter, converts it, and returns a Schedule.
     * @param dto ScheduleDto
     * @return Schedule
     */
    @Override
    public Schedule buildFromDto(ScheduleDto dto) {
        Schedule entity = new Schedule();
        entity.setScheduleType(ScheduleType.valueOf(dto.getType()));
        entity.setScheduleTime(dateUtil.stringToLocalDateTime(dto.getDateTimeSchedule()));
        return entity;
    }
}
