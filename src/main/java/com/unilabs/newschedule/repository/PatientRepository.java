package com.unilabs.newschedule.repository;

import com.unilabs.newschedule.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 An interface used as part of the data persistence mechanism, enabling the execution of
 data access operations related to the Patient class.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> getPatientByUtente(String utente);
}
