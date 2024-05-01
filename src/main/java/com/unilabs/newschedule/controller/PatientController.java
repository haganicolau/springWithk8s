package com.unilabs.newschedule.controller;

import com.unilabs.newschedule.dto.PatientDto;
import com.unilabs.newschedule.exception.ObjectAlreadyExistException;
import com.unilabs.newschedule.service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(PatientController.class);
    @Autowired
    private PatientService patientService;

    /**
     * A GET method that retrieves a patient by utente. If found, it returns the PatientDto; if not found, it
     * returns a 404 Not Found.
     *
     * @return ResponseEntity<?>
     */
    @GetMapping()
    public ResponseEntity<?> findByUtente(@RequestParam("utente") String utente) {
        LOG.info("Get request /patients?utente=" + utente);
        try {
            PatientDto dto = this.patientService.getPatientByUtenteAndConvertDto(utente);
            if (dto == null) {
                return getNotFound();
            }

            return ResponseEntity.ok().body(dto);
        } catch (Exception ex) {
            String message = String.format("Error while retrieving patient. Error: %s", ex.getMessage());
            LOG.error(message, ex);
            return getErrorMessage("Error while retrieving patient.");
        }
    }

    /**
     * POST method for patient registration.
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> createPatient(@Valid @RequestBody PatientDto dto) {
        LOG.info("Post request /patients");
        try {
            this.patientService.createPatient(dto);
            return ResponseEntity.ok().body(dto);
        } catch (ObjectAlreadyExistException ex) {
            String message = String.format("Error while signing up patient. Error: %s", ex.getMessage());
            LOG.error(message, ex);
            return getBadRequestMessage("Error while retrieving patient." + ex.getMessage());

        } catch (Exception ex) {
            String message = String.format("Error while signing up patient. Error: %s", ex.getMessage());
            LOG.error(message, ex);
            return getErrorMessage("Error while retrieving patient." + ex.getMessage());
        }
    }
}
