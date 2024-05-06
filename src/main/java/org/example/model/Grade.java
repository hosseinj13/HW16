package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Table(name = "grade")
@Entity
public class Grade extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "professor_id"  )
//    Professor professor;
  @Column(name = "professor_id")
    Long professorId;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "student_id")
//    Student student;
   @Column(name = "student_id")
    Long studentId;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "course_id")
    //Course course
    @Column(name = "course_id")
    Long courseId;



    @NotNull(message = "Grade is required")
    @Min(value = 0, message = "Grade must be at least 0")
    @Max(value = 20, message = "Grade must be at most 20")
    @Column(nullable = false)
    double grade;

    String semester;

    public static final int MAX_UNITS_WITH_HIGH_GPA = 24;
    public static final int MAX_UNITS_WITH_LOW_GPA = 20;
    public static final double HIGH_GPA_THRESHOLD = 18.0;
    public static final double MAX_HIGH_GRADE = 20.0;
    public static final double MIN_LOW_GRADE = 0.0;
    public final static double PASSING_GRADE = 10.0;

}
