package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.base.entity.BaseEntity;
import org.example.enums.AcademicTerm;
import org.example.model.user.Professor;
import org.example.model.user.Student;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@Entity
@Table(name = "enrollment")

public class Enrollment extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "student_id")
//    @NotNull(message = "Student is required")
    @Column(name = "student_id")
    Long studentId;


//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "course_id")
//    @NotNull(message = "Course is required")
    @Column(name = "course_id")
     Long courseId;


//    @Enumerated(EnumType.STRING)
//    @Column(name = "term_taking_course")
//    AcademicTerm term;

    String semester;
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "professor_id")
//    @NotNull(message = "Professor is required")
    @Column(name = "professor_id")
    Long professorId;
}

