package com.unilabs.newschedule.dto.factorydto;

import com.unilabs.newschedule.dto.AvailabilityDto;
import com.unilabs.newschedule.dto.ExamDto;
import com.unilabs.newschedule.model.Availability;
import com.unilabs.newschedule.model.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory that performs the conversion of the Exam entity to ExamDto and ExamDto
 * to Exam
 * @param <T> ExamDto
 * @param <F> Exam
 */
@Component
public class FactoryExamDto< T, F> implements IFactoryDto<ExamDto, Exam> {

    private final FactoryAvailabilityDto<AvailabilityDto, Availability> factoryAvailabilityDto;

    @Autowired
    public FactoryExamDto(FactoryAvailabilityDto<AvailabilityDto, Availability> factoryAvailabilityDto) {
        this.factoryAvailabilityDto = factoryAvailabilityDto;
    }

    /**
     * Receives an Exam as a parameter, converts it, and returns an ExamDto.
     * @param entity Exam
     * @return ExamDto
     */
    @Override
    public ExamDto buildFromEntity(Exam entity) {
        ExamDto dto = new ExamDto();
        dto.setCode(entity.getCode());
        dto.setDescription(entity.getDescription());
        dto.setName(entity.getName());
        dto.setAvailabilities(getAvailabilityDtoList(entity.getAvailabilities()));
        return dto;
    }

    /**
     * Receives an ExamDto as a parameter, converts it, and returns an Exam.
     * @param dto ExamDto
     * @return Exam
     */
    @Override
    public Exam buildFromDto(ExamDto dto) {
        Exam entity = new Exam();
        entity.setCode(dto.getCode());
        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());
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
     * @param exam Exam
     * @return list of availabilities
     */
    private List<Availability> getAvailabilityList(List<AvailabilityDto> availabilitiesDtoList, Exam exam) {
        List<Availability> list = new ArrayList<>();
        for (AvailabilityDto dto : availabilitiesDtoList) {
            Availability entity = this.factoryAvailabilityDto.buildFromDto(dto);
            entity.setExam(exam);
            list.add(entity);
        }
        return list;
    }
}
