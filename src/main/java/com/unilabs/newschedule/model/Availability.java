package com.unilabs.newschedule.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    protected AvailabilityStatus status;

    @Column(name = "dateTimeStart")
    private LocalDateTime dateTimeStart;

    @Column(name = "dateTimeEnd")
    private LocalDateTime dateTimeEnd;

    @ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "availability_weekdays", joinColumns = @JoinColumn(name = "availability_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "weekday")
    private List<DayOfWeek> weekdays;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "room_availability", joinColumns = @JoinColumn(name = "availability_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private Room room;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "exam_availability", joinColumns = @JoinColumn(name = "availability_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id"))
    private Exam exam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AvailabilityStatus getStatus() {
        return status;
    }

    public void setStatus(AvailabilityStatus status) {
        this.status = status;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public LocalDateTime getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(LocalDateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public List<DayOfWeek> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(List<DayOfWeek> weekdays) {
        this.weekdays = weekdays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Availability that = (Availability) o;
        return Objects.equals(id, that.id) && status == that.status
                && Objects.equals(dateTimeStart, that.dateTimeStart)
                && Objects.equals(dateTimeEnd, that.dateTimeEnd)
                && Objects.equals(weekdays, that.weekdays) && Objects.equals(room, that.room)
                && Objects.equals(exam, that.exam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, dateTimeStart, dateTimeEnd, weekdays, room, exam);
    }

    @Override
    public String toString() {
        return "Availability{" +
                "id=" + id +
                ", status=" + status +
                ", dateTimeStart=" + dateTimeStart +
                ", dateTimeEnd=" + dateTimeEnd +
                ", weekdays=" + weekdays +
                '}';
    }
}
