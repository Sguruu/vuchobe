package com.vuchobe.api.controller;

import com.vuchobe.api.json.JsonPage;
import com.vuchobe.api.model.v2.Lesson;
import com.vuchobe.api.service.LessonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/lesson")
public class LessonController {
    
    private final LessonService lessonService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@RequestBody Lesson lesson) {
        return lessonService.save(lesson).getId();
    }


    @GetMapping("")
    public Page<Lesson> get(Pageable pageable) {
        return new JsonPage<>(lessonService.get(pageable));
    }
    
}
