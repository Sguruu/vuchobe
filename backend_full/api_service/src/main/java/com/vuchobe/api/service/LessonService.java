package com.vuchobe.api.service;


import com.vuchobe.api.dao.LessonDao;
import com.vuchobe.api.model.v2.Lesson;
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
public class LessonService {
    private final LessonDao lessonDao;

    @Transactional
    public Lesson save(Lesson lesson) {
        return lessonDao.save(lesson);
    }

    public Page<Lesson> get(Pageable pageable) {
        return lessonDao.findAll(pageable);
    }
}
