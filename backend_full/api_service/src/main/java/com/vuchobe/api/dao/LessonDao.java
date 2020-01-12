package com.vuchobe.api.dao;

import com.vuchobe.api.model.v2.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonDao extends JpaRepository<Lesson, Long> {

//    Optional<Lesson> findByNameIgnoreCase(String name);
}
