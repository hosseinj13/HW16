package org.example.service.grade;

import jakarta.persistence.NoResultException;
import org.example.base.service.BaseService;
import org.example.enums.AcademicTerm;
import org.example.model.*;
import org.example.model.user.Professor;
import org.example.model.user.Student;

import java.util.List;

public interface GradeService extends BaseService<Grade, Long> {

    List<Course> getPassedCourses(Student student);

    List<Grade> registerStudentGradeByCourseId(Long id) throws NoResultException;

    List<Grade> registerStudentGradeByStudentId(Long id) throws NoResultException;

    List<Grade> registerStudentGradeByProfessorId(Long id) throws NoResultException;

    List<Grade> findGradeByStudent(Long studentId) throws  NoResultException;
    List<Object[]> findCourseNameAndGradeByStudent(Long studentId) throws NoResultException;
}
