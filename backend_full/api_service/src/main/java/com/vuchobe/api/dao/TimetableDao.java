package com.vuchobe.api.dao;

import com.vuchobe.api.model.v2.Lesson;
import com.vuchobe.api.model.v2.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableDao extends JpaRepository<Timetable, Long> {

}
