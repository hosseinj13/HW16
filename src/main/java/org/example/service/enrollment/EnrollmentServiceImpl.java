package org.example.service.enrollment;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.base.service.BaseServiceImpl;
import org.example.model.Course;
import org.example.model.Enrollment;
import org.example.model.Grade;
import org.example.model.user.Student;
import org.example.repository.enrollment.EnrollmentRepository;
import org.example.service.enrollment.EnrollmentService;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;


public class EnrollmentServiceImpl extends BaseServiceImpl<Enrollment, Long, EnrollmentRepository> implements EnrollmentService {


    public EnrollmentServiceImpl(EnrollmentRepository repository) {
        super(repository);
    }

    @Override
    public List<Enrollment> showAllEnrollments() {
        return repository.showAllEnrollments();
    }

    @Override
    public List<Course> findEnrolledCoursesByStudent(Student student) {
        return repository.findEnrolledCoursesByStudent(student);
    }


    @Override
    public List<Enrollment> enrollmentCourseByCourseId(Long id) throws NoResultException {
        return repository.enrollmentCourseByCourseId(id);
    }

    @Override
    public boolean isEnrolled(Long studentId, Long courseId) {
        return repository.isEnrolled(studentId, courseId);
    }

    @Override
    public List<String> findEnrolledCourseNamesByStudent(Long studentId) {
        return repository.findEnrolledCourseNamesByStudent(studentId);
    }
}
