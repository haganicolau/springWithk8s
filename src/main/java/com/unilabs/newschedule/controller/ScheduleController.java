package com.unilabs.newschedule.controller;

import com.unilabs.newschedule.dto.ScheduleDto;
import com.unilabs.newschedule.service.ScheduleService;
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
 * handles requests related the Schedule. The @RestController annotation indicates that this
 * class is responsible for providing HTTP responses in a format commonly used for developing REST APIs.
 */
@RestController
@RequestMapping("/schedules")
public class ScheduleController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleController.class);

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * A POST request that allows registering a schedule.
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ScheduleDto dto) {
        LOG.info("Post request /schedules");
        try {
            this.scheduleService.insertSchedule(dto);
            return ResponseEntity.ok().body(dto);
        } catch (DateTimeParseException ex) {
            String message = "Error while signing up schedule. Error: malformed data";
            LOG.error(message, ex);
            return getErrorMessage(message);

        } catch (Exception ex) {
            String message = String.format("Error while signing up schedule. Error: %s", ex.getMessage());
            LOG.error(message, ex);
            return getErrorMessage(message);
        }
    }
}
