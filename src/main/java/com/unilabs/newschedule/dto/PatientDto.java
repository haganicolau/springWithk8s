package com.unilabs.newschedule.dto;

import jakarta.validation.constraints.*;

import java.util.Objects;

/**
 * The PatientDto class, which implements the 'Data Transfer Object' pattern. The purpose of this DTO class
 * is to encapsulate data and transfer it independently of the entity that maps the database.
 *
 * Rules that are implemented:
 * -birhthDate is required
 * -birhthDate has a valid format: MM-DD-YYYY.
 * -utente is required and max size 9
 */
public class PatientDto {

    private static final Long serialVersionUID = 1L;

    @Pattern(regexp = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-\\d{4}$")
    @NotBlank
    @NotNull(message = "invalid birhth Ddate")
    private String birhthDate;

    @NotBlank(message = "invalid utente")
    @NotNull(message = "invalid utente")
    @Size(min = 9, max = 9)
    private String utente;

    private String name;

    public String getBirhthDate() {
        return birhthDate;
    }

    public void setBirhthDate(String birhthDate) {
        this.birhthDate = birhthDate;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PatientDto{" +
                "birhthDate=" + birhthDate +
                ", utente='" + utente + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientDto that = (PatientDto) o;
        return birhthDate.equals(that.birhthDate) && Objects.equals(utente, that.utente)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birhthDate, utente, name);
    }
}
