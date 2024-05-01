package com.unilabs.newschedule.service;

import com.unilabs.newschedule.dto.ScheduleDto;
import com.unilabs.newschedule.dto.factorydto.FactoryScheduleDto;
import com.unilabs.newschedule.exception.ObjectNotFoundException;
import com.unilabs.newschedule.model.*;
import com.unilabs.newschedule.repository.ScheduleRepository;
import com.unilabs.newschedule.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Schedule services class
 */
@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private PatientService patientService;
    private ExamService examService;
    private RoomService roomService;
    private final DateUtil dateUtil;

    @Autowired
    public ScheduleService (
            ScheduleRepository scheduleRepository,
            PatientService patientService,
            ExamService examService,
            RoomService roomService,
            DateUtil dateUtil,
            FactoryScheduleDto<ScheduleDto, Schedule> factoryScheduleDto
    ) {
        this.scheduleRepository = scheduleRepository;
        this.patientService = patientService;
        this.examService = examService;
        this.roomService = roomService;
        this.dateUtil = dateUtil;
        this.factoryScheduleDto = factoryScheduleDto;
    }

    private FactoryScheduleDto<ScheduleDto, Schedule> factoryScheduleDto;

    /**
     * Method containing the business rules for schedule registration.
     * 1- Get patient by utente.
     * 2- Define if the schedule is for exam or room
     * 3- convert dateToSchedule into LocalDateTime
     * 4- get a list of exams or rooms available. If there is no room/exam available, an exception will be thrown
     * 5- Get Available Availability
     * 6- set availability like Unavailable
     * 7- insert in database
     *
     * @param roomDto roomDto
     */
    public void insertSchedule(ScheduleDto dto) {
        Patient patient = patientService.getByUtente(dto.getUtentePatient());

        Schedule schedule = this.factoryScheduleDto.buildFromDto(dto);
        schedule.setPatient(patient);

        Availability availability = null;
        if (dto.getType().equals(ScheduleType.EXAM.name())) {
            LocalDateTime dateToSchedule = dateUtil.stringToLocalDateTime(dto.getDateTimeSchedule());
            List<Exam> exams = examService.getByCodeAndScheduleTime(dto.getCodeRoomExam(), dateToSchedule);

            if (exams.isEmpty()) {
                throw new ObjectNotFoundException("Not availability for this exam!");
            }

            schedule.setExam(exams.get(0));
            availability = getAvailableAvailability(exams.get(0).getAvailabilities(), dateToSchedule);

        } else {
            LocalDateTime dateToSchedule = dateUtil.stringToLocalDateTime(dto.getDateTimeSchedule());
            List<Room> rooms = roomService.getByCodeAndScheduleTime(dto.getCodeRoomExam(), dateToSchedule);

            if (rooms.isEmpty()) {
                throw new ObjectNotFoundException("Not availability for this room!");
            }

            schedule.setRoom(rooms.get(0));
            availability = getAvailableAvailability(rooms.get(0).getAvailabilities(), dateToSchedule);
        }

        if (availability == null) {
            throw new ObjectNotFoundException("Not availability found!");
        }

        availability.setStatus(AvailabilityStatus.UNAVAILABLE);
        schedule.setAvailability(availability);
        this.scheduleRepository.save(schedule);
    }

    /**
     * get Available Availability in list
     * @param availabilityList
     * @param dateToSchedule
     * @return
     */
    public Availability getAvailableAvailability(List<Availability> availabilityList, LocalDateTime dateToSchedule) {
        for (Availability availability : availabilityList) {
            if (availability.getStatus().equals(AvailabilityStatus.UNAVAILABLE)) {
                continue;
            }

            if (dateToSchedule.isAfter(availability.getDateTimeStart())
                    && dateToSchedule.isBefore(availability.getDateTimeEnd())) {
                return availability;
            }
        }
        return null;
    }
}
