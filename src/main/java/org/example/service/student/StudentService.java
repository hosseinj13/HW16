package org.example.service.student;

import jakarta.persistence.NoResultException;
import org.example.base.service.BaseService;
import org.example.model.Course;
import org.example.model.user.Student;

import java.util.List;

public interface StudentService extends BaseService<Student, Long> {

    Student findByFullName(String firstName, String lastName) throws NoResultException;



}
