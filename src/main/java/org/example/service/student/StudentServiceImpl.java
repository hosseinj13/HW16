package org.example.service.student;

import jakarta.persistence.NoResultException;
import jakarta.validation.ConstraintViolationException;
import org.example.base.service.BaseServiceImpl;
import org.example.model.Course;
import org.example.model.Grade;
import org.example.model.user.Student;
import org.example.repository.student.StudentRepository;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl extends BaseServiceImpl<Student, Long, StudentRepository> implements StudentService {

    public StudentServiceImpl(StudentRepository repository) {
        super(repository);
    }



    @Override
    public Student findByFullName(String firstName, String lastName) throws NoResultException {
        return repository.findByFullName(firstName,lastName);

    }


}
