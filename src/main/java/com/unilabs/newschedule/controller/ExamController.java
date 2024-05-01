package com.unilabs.newschedule.controller;

import com.unilabs.newschedule.dto.ExamDto;
import com.unilabs.newschedule.exception.ObjectAlreadyExistException;
import com.unilabs.newschedule.service.ExamService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeParseException;

/**
 * That class, annotated with @RestController, is a controller in a Spring application that
 * handles requests related the Exam and the availabilities. The @RestController annotation indicates that this
 * class is responsible for providing HTTP responses in a format commonly used for developing REST APIs.
 */
@RestController
@RequestMapping("/exams")
public class ExamController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(ExamController.class);

    private ExamService roomService;

    @Autowired
    public ExamController(ExamService roomService) {
        this.roomService = roomService;
    }

    /**
     * It handles requests to create an exam
     * @param dto ExamDto
     * @return ResponseEntity<?>
     * @throws DateTimeParseException malformed data
     * @throws Exception
     */
    @PostMapping
    public ResponseEntity<?> createExam(@Valid @RequestBody ExamDto dto) {
        LOG.info("Post request /exams");
        try {
            this.roomService.insertExam(dto);
            return ResponseEntity.ok().body(dto);
        } catch (DateTimeParseException ex) {
            String message = "Error while signing up exam. Error: malformed data";
            LOG.error(message, ex);
            return getBadRequestMessage(message);

        } catch (ObjectAlreadyExistException ex) {
            String message = String.format("Error while signing up patient. Error: %s", ex.getMessage());
            LOG.error(message, ex);
            return getBadRequestMessage("Error while retrieving patient." + ex.getMessage());

        } catch (Exception ex) {
            String message = String.format("Error while signing up exam. Error: %s", ex.getMessage());
            LOG.error(message, ex);
            return getErrorMessage(message);
        }
    }
}
