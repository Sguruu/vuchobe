package com.vuchobe.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.vuchobe.api.json.JsonPage;
import com.vuchobe.api.model.v2.Lesson;
import com.vuchobe.api.model.v2.Timetable;
import com.vuchobe.api.service.LessonService;
import com.vuchobe.api.service.TimetableService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/timetable")
public class TimetableController {
    
    private final TimetableService timetableService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@JsonView(Timetable.View.Save.class) @RequestBody Timetable timetable) {
        return timetableService.save(timetable).getId();
    }


    @GetMapping("")
    @JsonView(Timetable.View.List.class)
    public Page<Timetable> get(Pageable pageable) {
        return new JsonPage<>(timetableService.get(pageable));
    }
    
}
