package org.example.service.course;

import jakarta.persistence.NoResultException;
import org.example.base.service.BaseServiceImpl;
import org.example.model.Course;
import org.example.model.user.Student;
import org.example.repository.course.CourseRepository;
import org.example.service.course.CourseService;
import org.hibernate.SessionFactory;

import java.util.List;

public class CourseServiceImpl extends BaseServiceImpl<Course, Long, CourseRepository> implements CourseService {

    public CourseServiceImpl(CourseRepository repository) {
        super(repository);
    }


    @Override
    public Course findByCourseName(String courseName) throws NoResultException {
        return repository.findByCourseName(courseName);
    }


    @Override
    public List<Course> getAvailableCourses(Student student) {
        return repository.findAvailableCourses();
    }
}