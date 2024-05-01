package com.unilabs.newschedule.service;

import com.unilabs.newschedule.dto.PatientDto;
import com.unilabs.newschedule.dto.factorydto.FactoryPatientDto;
import com.unilabs.newschedule.exception.ObjectAlreadyExistException;
import com.unilabs.newschedule.model.Patient;
import com.unilabs.newschedule.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    FactoryPatientDto<PatientDto, Patient> factoryPatientDto;

    @Autowired
    public PatientService(PatientRepository patientRepository,
                          FactoryPatientDto<PatientDto, Patient> factoryPatientDto) {
        this.patientRepository = patientRepository;
        this.factoryPatientDto = factoryPatientDto;
    }

    /**
     * Method containing the business rules for patient registration.
     * 1- Check if the utente is not already registered.
     * 2- Convert the PatientDto received from the request into the Patient entity.
     * 3- Save the patient in the database.
     *
     * @param dto PatientDto
     */
    public void createPatient(PatientDto dto) {
        if (this.getByUtente(dto.getUtente()) != null) {
            throw new ObjectAlreadyExistException("Utente already exists");
        }

        Patient patient = this.factoryPatientDto.buildFromDto(dto);
        this.patientRepository.save(patient);
    }

    /**
     * Get Patient by utente and convert entity into dto
     *
     * @param utente utente
     * @return PatientDto dto
     */
    public PatientDto getPatientByUtenteAndConvertDto(String utente) {
        Patient patient = this.getByUtente(utente);
        return patient == null ? null : this.factoryPatientDto.buildFromEntity(patient);
    }

    /**
     * Get Patient by utente
     *
     * @param utente utente
     * @return Patient entity
     */
    public Patient getByUtente(String utente) {
        Optional<Patient> optionalPatient = this.patientRepository.getPatientByUtente(utente);
        return optionalPatient.orElse(null);
    }
}