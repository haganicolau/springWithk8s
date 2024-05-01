package com.unilabs.newschedule.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "birhthdate", nullable = true)
    private LocalDate birhthDate;

    @Column(name = "utente", nullable = false, length = 9)
    private String utente;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirhthDate() {
        return birhthDate;
    }

    public void setBirhthDate(LocalDate birhthDate) {
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
        return "Patient{" +
                "id=" + id +
                ", birhthDate=" + birhthDate +
                ", utente='" + utente + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) && Objects.equals(birhthDate, patient.birhthDate)
                && Objects.equals(utente, patient.utente) && Objects.equals(name, patient.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, birhthDate, utente, name);
    }
}
