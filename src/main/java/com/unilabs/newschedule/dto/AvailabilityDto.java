package com.unilabs.newschedule.dto;

import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.List;

/**
 * The AvailabilityDto class, which implements the 'Data Transfer Object' pattern. The purpose of this DTO class
 * is to encapsulate data and transfer it independently of the entity that maps the database.
 *
 * Rules that are implemented:
 * -AVAILABLE and UNAVAILABLE are the valid states.
 * -dateTimeEnd has a valid format: MM-DD-YYYY HH:MM:SS.
 * -dateTimeStart has a valid format: MM-DD-YYYY HH:MM:SS.
 */
public class AvailabilityDto implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Pattern(regexp = "^(AVAILABLE|UNAVAILABLE)$", message = "invalid status")
    private String status;

    @Pattern(regexp = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-(\\d{4} \\d{2}:\\d{2}:\\d{2})$",
            message = "invalid date: the format needs to be: MM-DD-YYYY HH:MM:SS")
    private String dateTimeEnd;

    @Pattern(regexp = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-(\\d{4} \\d{2}:\\d{2}:\\d{2})$",
            message = "invalid date: the format needs to be: MM-DD-YYYY HH:MM:SS")
    private String dateTimeStart;

    private List<String> daysOfWeek;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<String> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(String dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public String getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(String dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    @Override
    public String toString() {
        return "AvailabilityDto{" +
                "status='" + status + '\'' +
                ", dateTimeEnd='" + dateTimeEnd + '\'' +
                ", dateTimeStart='" + dateTimeStart + '\'' +
                ", daysOfWeek=" + daysOfWeek +
                '}';
    }
}
