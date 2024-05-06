package org.example.repository.enrollment;

import jakarta.persistence.NoResultException;
import org.example.base.repository.BaseRepository;
import org.example.model.Course;
import org.example.model.Enrollment;
import org.example.model.Grade;
import org.example.model.user.Student;

import java.util.List;

public interface EnrollmentRepository extends BaseRepository<Enrollment, Long>{

    List<Enrollment> showAllEnrollments() throws NoResultException;
    List<Course> findEnrolledCoursesByStudent(Student student);
    List<Enrollment> enrollmentCourseByCourseId(Long id) throws NoResultException;
     boolean isEnrolled(Long student, Long course);
     List<String> findEnrolledCourseNamesByStudent(Long studentId);
}
