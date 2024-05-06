package org.example.repository.course;

import jakarta.persistence.NoResultException;
import org.example.base.repository.BaseRepository;
import org.example.model.Course;
import org.example.model.user.Student;

import java.util.List;

public interface CourseRepository extends BaseRepository<Course, Long> {


    List<Course> findAvailableCourses();
    Course findByCourseName(String name) throws NoResultException;


}
