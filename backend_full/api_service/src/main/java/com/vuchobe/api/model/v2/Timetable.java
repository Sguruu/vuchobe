package com.vuchobe.api.model.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.vuchobe.api.views.Views;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "timetable")
@EqualsAndHashCode(of = {"id", "start", "end"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonView({View.Save.class, View.List.class})
    private Long id;

    @Column(name = "start_lesson")
    @JsonView({View.Save.class, View.List.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;

    @Column(name = "end_lesson")
    @JsonView({View.Save.class, View.List.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;

    /*TODO добавить ID*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    @JsonView({Timetable.View.List.class})
    private Faculty faculty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_group_id")
    @JsonView({Timetable.View.List.class})
    private StudentGroup studentGroup;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    @JsonView({View.Save.class, View.List.class})
    private Lesson lesson;

    
    @Transient
    @JsonView(View.Save.class)
    private Long facultyId;
    
    @Transient
    @JsonView(View.Save.class)
    private Long studentGroupId;    
    
    @Transient
    @JsonView(View.Save.class)
    private Long lessonId;

    public static class View {
        public static class Save {};

        public static class Update extends View.Save {};

        public static class List extends Views.List {};
    }
}
