package com.unilabs.newschedule.dto.factorydto;

import com.unilabs.newschedule.dto.PatientDto;
import com.unilabs.newschedule.model.Patient;
import com.unilabs.newschedule.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A factory that performs the conversion of the Patient entity to PatientDto and PatientDto
 * to Patient
 * @param <T> PatientDto
 * @param <F> Patient
 */
@Component
public class FactoryPatientDto<T, F> implements IFactoryDto<PatientDto, Patient> {

    private final DateUtil dateUtil;

    @Autowired
    public FactoryPatientDto(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    /**
     * Receives a Patient as a parameter, converts it, and returns a PatientDto.
     * @param entity Patient
     * @return PatientDto
     */
    @Override
    public PatientDto buildFromEntity(Patient entity) {
        PatientDto dto = new PatientDto();
        dto.setUtente(entity.getUtente());
        dto.setName(entity.getName());
        dto.setBirhthDate(dateUtil.localDateToString(entity.getBirhthDate()));
        return dto;
    }

    /**
     * Receives a PatientDto as a parameter, converts it, and returns a Patient.
     * @param dto PatientDto
     * @return Patient
     */
    @Override
    public Patient buildFromDto(PatientDto dto) {
        Patient patient = new Patient();
        patient.setBirhthDate(dateUtil.stringToLocalDate(dto.getBirhthDate()));
        patient.setUtente(dto.getUtente());
        patient.setName(dto.getName());
        return patient;
    }

}
