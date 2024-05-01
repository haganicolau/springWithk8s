package com.unilabs.newschedule.service;

import com.unilabs.newschedule.dto.ExamDto;
import com.unilabs.newschedule.dto.factorydto.FactoryExamDto;
import com.unilabs.newschedule.exception.DateTimeConflictException;
import com.unilabs.newschedule.exception.ObjectAlreadyExistException;
import com.unilabs.newschedule.model.Availability;
import com.unilabs.newschedule.model.Exam;
import com.unilabs.newschedule.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Exam services class
 */
@Service
public class ExamService {

    ExamRepository examRepository;
    FactoryExamDto<ExamDto, Exam> examFactory;

    @Autowired
    public ExamService(ExamRepository examRepository, FactoryExamDto<ExamDto, Exam> examFactory) {
        this.examRepository = examRepository;
        this.examFactory = examFactory;
    }

    /**
     * Check if there are no conflict in dates
     * @param code code to find
     * @param dateTimeStart data time start
     * @param dateTimeEnd data time to end
     * @return boolean
     */
    public boolean isTimeAvailable(String code, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd) {
        long collisionCount = this.examRepository.countCollisions(dateTimeStart, dateTimeEnd, code);
        return collisionCount == 0;
    }

    /**
     *Method containing the business rules for exam registration.
     * 1- Check if the code is not already registered.
     * 2- Convert the ExamDto received from the request into the Exam entity.
     * 3- Check in the list of availabilities for any conflicts; if conflicting dates are found, it will raise an exception.
     * 4- Save the exam in the database.
     *
     * @param dto examDto
     */
    public void insertExam(ExamDto dto) {
        if (this.getByCode(dto.getCode()) != null) {
            throw new ObjectAlreadyExistException("Code already exists");
        }

        Exam exam = this.examFactory.buildFromDto(dto);

        for (Availability availability : exam.getAvailabilities()) {
            boolean isAvailable = isTimeAvailable(exam.getCode(),
                    availability.getDateTimeStart(), availability.getDateTimeEnd());

            if (!isAvailable) {
                throw new DateTimeConflictException("Unable to create availabilities, conflict dates");
            }
        }

        this.examRepository.save(exam);
    }

    /**
     * Get Exam by code
     * @param code code
     * @return Exam
     */
    public Exam getByCode(String code) {
        Optional<Exam> optionalRoom = examRepository.findByCode(code);

        return optionalRoom.orElse(null);
    }

    /**
     * Retrieve a list of available exams using a date for consultation and the exam code.
     * @param code code to find
     * @param scheduleTime time to check availability
     * @return
     */
    public List<Exam> getByCodeAndScheduleTime(String code, LocalDateTime scheduleTime) {
        return this.examRepository.findExamByCodeAndDateTime(code,scheduleTime);
    }
}
