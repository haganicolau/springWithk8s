package com.unilabs.newschedule.controller;

import com.unilabs.newschedule.dto.RoomDto;
import com.unilabs.newschedule.exception.ObjectAlreadyExistException;
import com.unilabs.newschedule.service.RoomService;
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
 * handles requests related the Room and the availabilities. The @RestController annotation indicates that this
 * class is responsible for providing HTTP responses in a format commonly used for developing REST APIs.
 */
@RestController
@RequestMapping("/rooms")
public class RoomController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    /**
     * A POST request that allows registering a room.
     * @param dto roomDto
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createPatient(@Valid @RequestBody RoomDto dto) {
        LOG.info("Post request /rooms");
        try {
            this.roomService.insertRoom(dto);
            return ResponseEntity.ok().body(dto);
        } catch (DateTimeParseException ex) {
            String message = "Error while signing up room. Error: malformed data";
            LOG.error(message, ex);
            return getBadRequestMessage(message);

        } catch (ObjectAlreadyExistException ex) {
            String message = String.format("Error while signing up patient. Error: %s", ex.getMessage());
            LOG.error(message, ex);
            return getBadRequestMessage("Error while retrieving patient." + ex.getMessage());

        } catch (Exception ex) {
            String message = String.format("Error while signing up room. Error: %s", ex.getMessage());
            LOG.error(message, ex);
            return getErrorMessage(message);
        }
    }
}
