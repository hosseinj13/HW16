package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.base.entity.BaseEntity;
import org.example.enums.AcademicTerm;
import org.example.enums.CourseStatus;
import org.example.enums.Department;
import org.example.model.user.Professor;
import org.example.model.user.Student;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@Entity
@Table(name = "course")
public class Course extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    @NotBlank(message = "Course name is required")
    @Column(name = "course_name")
     String courseName;

    /*@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "professor_id")
    @NotNull(message = "Professor is required")
    Professor professor;*/

//    @OneToOne (cascade = CascadeType.ALL)
//    @JoinColumn(name = "student_id")
//    @NotNull(message = "Student is required")


    String semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "term_presentation_course")
    AcademicTerm term;


    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    Department department;

//   @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
//   Grade grades ;


    @Column(name = "unit_course")
     int units;

    /*@Enumerated(EnumType.STRING)
    @Column(name = "course_status")
    CourseStatus courseStatus;*/
}