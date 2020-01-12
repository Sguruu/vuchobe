package com.vuchobe.api.model.v2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.vuchobe.api.views.Views;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "address")
@Data
@EqualsAndHashCode(of = {"id", "fullAddress"})
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    @JsonView({Views.List.class, Institute.View.Save.class,
            Faculty.View.Save.class, Activity.View.Save.class, Timetable.View.List.class})
    private Long id;

    @Column(length = 550)
    @JsonView({Views.List.class, Institute.View.Save.class, 
            Faculty.View.Save.class, Activity.View.Save.class, Timetable.View.List.class, 
            Activity.View.Save.class, Activity.View.List.class})
    private String fullAddress;
    @Column
    private String city;
    @Column
    private String street;
    
    @Column
    private String house;
    @Column
    private String index;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Faculty faculty;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Institute institute;
    
    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private Set<Activity> activity;
}
