package org.example.service.enrollment;

import jakarta.persistence.NoResultException;
import org.example.base.service.BaseService;
import org.example.model.Course;
import org.example.model.Enrollment;
import org.example.model.user.Student;

import java.util.List;

public interface EnrollmentService extends BaseService<Enrollment, Long> {

    List<Enrollment> showAllEnrollments() throws NoResultException;

    List<Course> findEnrolledCoursesByStudent(Student student);

    List<Enrollment> enrollmentCourseByCourseId(Long id) throws NoResultException;

    public boolean isEnrolled(Long studentId, Long courseId);

    List<String> findEnrolledCourseNamesByStudent(Long studentId);
}
