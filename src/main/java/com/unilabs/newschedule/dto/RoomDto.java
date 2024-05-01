package com.unilabs.newschedule.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * The RoomDto class, which implements the 'Data Transfer Object' pattern. The purpose of this DTO class
 * is to encapsulate data and transfer it independently of the entity that maps the database.
 *
 * Rules that are implemented:
 * -code is required
 * -description is required
 * -availabilities is required
 */
public class RoomDto implements Serializable {

    private static final Long serialVersionUID = 1L;

    @NotBlank(message = "invalid code")
    @NotNull(message = "invalid code")
    private String code;

    @NotBlank(message = "invalid description")
    @NotNull(message = "invalid description")
    private String description;

    @Valid
    @NotEmpty(message = "empty availability")
    private List<AvailabilityDto> availabilities;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AvailabilityDto> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<AvailabilityDto> availabilities) {
        this.availabilities = availabilities;
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", availabilities=" + availabilities +
                '}';
    }
}
