package org.example.repository.grade;

import jakarta.persistence.NoResultException;
import org.example.base.repository.BaseRepository;
import org.example.model.Course;
import org.example.model.Grade;
import org.example.model.user.Student;

import java.util.List;

public interface GradeRepository extends BaseRepository<Grade, Long> {
    List<Course> findPassedCoursesByStudent(Student student);
    List<Grade> registerStudentGradeByCourseId(Long id) throws NoResultException;
    List<Grade> registerStudentGradeByStudentId(Long id) throws NoResultException;
    List<Grade> registerStudentGradeByProfessorId(Long id) throws NoResultException;
    List<Grade> findGradeByStudent(Long studentId) throws  NoResultException;
    List<Object[]> findCourseNameAndGradeByStudent(Long studentId) throws NoResultException;
}
