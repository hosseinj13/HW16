package org.example.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.enums.AcademicTerm;
import org.example.model.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@Entity
public class Student extends User {

    @NotBlank(message = "Student number is required")
    @Column(name = "student number", unique = true)
    String studentNumber;

  /*  @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee advisor;*/

   /* @ManyToOne
    @JoinColumn(name = "professor_id")
    @NotNull(message = "Professor is required")
    Professor professor;*/


    @Column(name = "previous_gpa")
    @DecimalMin(value = "0.0", message = "Previous GPA must be greater than or equal to 0.0")
    @DecimalMax(value = "20.0", message = "Previous GPA must be less than or equal to 20.0")
    Double previousGPA;


    @Enumerated(EnumType.STRING)
    @Column(name = "current_term")
    AcademicTerm currentTerm;

    String semester;

//    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
//    @Column(name = "course_name")
//    String courseName;


//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
//    List<Course> passedCourses;
//
//
//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
//    List<Course> availableCourses ;

//    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
//    Grade grade ;


}
