package com.vuchobe.api.model.v2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "lesson")
@Data
@NoArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    @JsonView({Timetable.View.List.class})
    private String fullName;

    @Column()
    @JsonView({Timetable.View.List.class})
    private String shortName;
    
    @Column(length = 500)
    @JsonView({Timetable.View.List.class})
    private String description;

    @OneToMany(mappedBy = "lesson")
    @JsonIgnore
    private Set<Timetable> timetables = new HashSet<>();

    @Singular
    @OneToMany(mappedBy = "lesson")
    @JsonIgnore
    private Set<StudentMark> studentMarks;

}
