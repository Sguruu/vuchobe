package com.vuchobe.api.service;

import com.vuchobe.api.dao.*;
import com.vuchobe.api.model.v2.Faculty;
import com.vuchobe.api.model.v2.Lesson;
import com.vuchobe.api.model.v2.StudentGroup;
import com.vuchobe.api.model.v2.Timetable;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
        readOnly = true
)
@AllArgsConstructor
public class TimetableService {
    private final TimetableDao timetableDao;
    private final StudentGroupDao studentGroupDao;
    private final FacultyDao facultyDao;
    private final LessonDao lessonDao;

    @Transactional
    public Timetable save(Timetable timetable) {
        if (timetable.getFacultyId() == null && timetable.getStudentGroupId() == null) 
            throw new NullPointerException("Укажите один из обязательных аттрибутов facultyId или studentGroupId");
        Lesson lesson = lessonDao.findById(timetable.getLessonId())
                .orElseThrow(NullPointerException::new);
        timetable.setLesson(lesson);
        if (timetable.getStudentGroupId() != null) {
            StudentGroup studentGroup = studentGroupDao.findById(timetable.getStudentGroupId())
                    .orElseThrow(NullPointerException::new);
            timetable.setStudentGroup(studentGroup);
        }        
        if (timetable.getFacultyId() != null) {
            Faculty faculty = facultyDao.findById(timetable.getFacultyId())
                    .orElseThrow(NullPointerException::new);
            timetable.setFaculty(faculty);
        }
        return timetableDao.save(timetable);
    }

    public Page<Timetable> get(Pageable pageable) {
        return timetableDao.findAll(pageable);
    }
}
