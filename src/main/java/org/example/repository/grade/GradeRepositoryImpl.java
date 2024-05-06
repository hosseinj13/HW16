package org.example.repository.grade;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.base.repository.BaseRepositoryImpl;
import org.example.conncetion.SessionFactorySingleton;
import org.example.model.Course;
import org.example.model.Grade;
import org.example.model.user.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class GradeRepositoryImpl extends BaseRepositoryImpl<Grade, Long> implements GradeRepository {

    public GradeRepositoryImpl(Session session) {
        super(session);
    }


    @Override
    public Class<Grade> getEntityClass() {
        return Grade.class;
    }

    @Override
    public List<Course> findPassedCoursesByStudent(Student student) {
        String jpql = "SELECT g.courseId FROM Grade g WHERE g.studentId = :student AND g.grade >= :passingGrade";
        TypedQuery<Course> query = session.createQuery(jpql, Course.class);
        query.setParameter("student", student);
        query.setParameter("passingGrade", Grade.PASSING_GRADE);
        return query.getResultList();
    }

    @Override
    public List<Grade> registerStudentGradeByCourseId(Long id) throws NoResultException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Grade> query = session.createQuery(" FROM Grade g WHERE g.courseId =:id",
                Grade.class);
        query.setParameter("id", id);
        List<Grade> grades = query.list();
        session.close();
        return grades;
    }

    @Override
    public List<Grade> registerStudentGradeByStudentId(Long id) throws NoResultException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Grade> query = session.createQuery(" FROM Grade g WHERE g.studentId =:id",
                Grade.class);
        query.setParameter("id", id);
        List<Grade> grades = query.list();
        session.close();
        return grades;
    }

    @Override
    public List<Grade> registerStudentGradeByProfessorId(Long id) throws NoResultException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Grade> query = session.createQuery(" FROM Grade g WHERE g.professorId =:id",
                Grade.class);
        query.setParameter("id", id);
        List<Grade> grades = query.list();
        session.close();
        return grades;
    }


    @Override
    public List<Grade> findGradeByStudent(Long studentId) throws NoResultException {
        String jpql = "SELECT g FROM Grade g WHERE g.studentId = :studentId";
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Grade> query = session.createQuery(jpql, Grade.class);
        query.setParameter("studentId", studentId);
        List<Grade> grades = query.list();
        session.close();
        return grades;
    }

    @Override
    public List<Object[]> findCourseNameAndGradeByStudent(Long studentId) throws NoResultException {
        String jpql = "SELECT c.courseName, g.grade " +
                "FROM Grade g " +
                "JOIN Course c ON g.courseId = c.id " +
                "WHERE g.studentId = :studentId";
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Object[]> query = session.createQuery(jpql);
        query.setParameter("studentId", studentId);
        List<Object[]> results = query.list();
        session.close();
        return results;
    }
}

