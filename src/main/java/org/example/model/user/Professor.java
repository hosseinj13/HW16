package org.example.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.enums.AcademicTerm;
import org.example.model.Course;
import org.example.enums.ProfessorType;
import org.example.model.Grade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@Entity
public class Professor extends User {


   /* @OneToMany(mappedBy ="professor",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    List<Course> courses;*/


  /*  @OneToMany(mappedBy ="student",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    List<Student> students;*/

    @NotBlank(message = "Professor Number is required")
    @Column(name = "professor_number", unique = true)
    String professorNumber;

    @Enumerated(EnumType.STRING)
    ProfessorType professorType;

    @PositiveOrZero(message = "Teaching units must be a positive number or zero")
    int teachingUnits;

    String semester;


//    @OneToOne(mappedBy = "professor", cascade = CascadeType.ALL)
//    Grade grade ;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_term")
    AcademicTerm currentTerm;

    public static final double UNIT_SALARY = 20000000;

}