package org.example.service.course;

import jakarta.persistence.NoResultException;
import org.example.base.service.BaseService;
import org.example.model.Course;
import org.example.model.user.Student;

import java.util.List;

public interface CourseService extends BaseService<Course, Long> {

    Course findByCourseName(String courseName) throws NoResultException;



    List<Course> getAvailableCourses(Student student);
}
