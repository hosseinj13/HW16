package org.example.service.grade;

import jakarta.persistence.NoResultException;
import org.example.base.service.BaseServiceImpl;
import org.example.enums.AcademicTerm;
import org.example.model.*;
import org.example.model.user.Professor;
import org.example.model.user.Student;
import org.example.repository.grade.GradeRepository;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class GradeServiceImpl extends BaseServiceImpl<Grade, Long, GradeRepository> implements GradeService {

    public GradeServiceImpl(GradeRepository repository ) {
        super(repository);
    }


    @Override
    public List<Course> getPassedCourses(Student student) {
        return repository.findPassedCoursesByStudent(student);
    }

    @Override
    public List<Grade>  registerStudentGradeByCourseId(Long id) throws NoResultException {
        return repository.registerStudentGradeByCourseId(id);
    }

    @Override
    public List<Grade> registerStudentGradeByStudentId(Long id) throws NoResultException {
        return repository.registerStudentGradeByStudentId(id);
    }

    @Override
    public List<Grade> registerStudentGradeByProfessorId(Long id) throws NoResultException {
        return repository.registerStudentGradeByProfessorId(id);
    }

    @Override
    public List<Grade> findGradeByStudent(Long studentId) throws NoResultException {
        return repository.findGradeByStudent(studentId);
    }

    @Override
    public List<Object[]> findCourseNameAndGradeByStudent(Long studentId) throws NoResultException {
        return repository.findCourseNameAndGradeByStudent(studentId);
    }
}
