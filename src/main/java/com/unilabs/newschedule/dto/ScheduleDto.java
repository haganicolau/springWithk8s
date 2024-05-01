package com.unilabs.newschedule.dto;

//import com.unilabs.newschedule.dto.vaildators.PatientNotExists;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

/**
 * The RoomDto class, which implements the 'Data Transfer Object' pattern. The purpose of this DTO class
 * is to encapsulate data and transfer it independently of the entity that maps the database.
 *
 * Rules that are implemented:
 * -codeRoomExam is required
 * -utentePatient is required
 * -utentePatient: patient needs to be added before
 * -dateTimeSchedule has a valid format: MM-DD-YYYY HH:MM:SS.
 * -ROOM or EXAM area a valid type
 * -type is re
 *
 */
public class ScheduleDto implements Serializable {

    private static final Long serialVersionUID = 1L;

    @NotBlank(message = "invalid codeRoomExam")
    @NotNull(message = "invalid codeRoomExam")
    private String codeRoomExam;

    //@PatientNotExists
    @NotBlank(message = "invalid utente")
    @NotNull(message = "invalid utente")
    private String utentePatient;

    @Pattern(regexp = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-(\\d{4} \\d{2}:\\d{2}:\\d{2})$",
            message = "invalid date: the format needs to be: MM-DD-YYYY HH:MM:SS")
    private String dateTimeSchedule;

    @NotBlank(message = "invalid type")
    @NotNull(message = "invalid type")
    @Pattern(regexp = "^(ROOM|EXAM)$", message = "invalid type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCodeRoomExam() {
        return codeRoomExam;
    }

    public void setCodeRoomExam(String codeRoomExam) {
        this.codeRoomExam = codeRoomExam;
    }

    public String getUtentePatient() {
        return utentePatient;
    }

    public void setUtentePatient(String utentePatient) {
        this.utentePatient = utentePatient;
    }

    public String getDateTimeSchedule() {
        return dateTimeSchedule;
    }

    public void setDateTimeSchedule(String dateTimeSchedule) {
        this.dateTimeSchedule = dateTimeSchedule;
    }

    @Override
    public String toString() {
        return "ScheduleDto{" +
                "codeRoomExam='" + codeRoomExam + '\'' +
                ", utentePatient='" + utentePatient + '\'' +
                ", dateTimeSchedule='" + dateTimeSchedule + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
