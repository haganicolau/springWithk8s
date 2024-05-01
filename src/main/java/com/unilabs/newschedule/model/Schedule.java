package com.unilabs.newschedule.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "availability_id")
    private Availability availability;

    @Enumerated(EnumType.STRING)
    protected ScheduleType scheduleType;

    @Column(name = "scheduleTime")
    private LocalDateTime scheduleTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public ScheduleType getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    public LocalDateTime getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(LocalDateTime scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id)
                && Objects.equals(patient, schedule.patient)
                && Objects.equals(exam, schedule.exam)
                && Objects.equals(room, schedule.room)
                && Objects.equals(availability, schedule.availability)
                && scheduleType == schedule.scheduleType
                && Objects.equals(scheduleTime, schedule.scheduleTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patient, exam, room, availability, scheduleType, scheduleTime);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", patient=" + patient +
                ", exam=" + exam +
                ", room=" + room +
                ", availability=" + availability +
                ", scheduleType=" + scheduleType +
                ", scheduleTime=" + scheduleTime +
                '}';
    }
}
