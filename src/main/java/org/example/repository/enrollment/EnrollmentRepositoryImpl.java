package org.example.repository.enrollment;


import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.base.repository.BaseRepositoryImpl;
import org.example.conncetion.SessionFactorySingleton;
import org.example.model.Course;
import org.example.model.Enrollment;
import org.example.model.Grade;
import org.example.model.user.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class EnrollmentRepositoryImpl extends BaseRepositoryImpl<Enrollment, Long> implements EnrollmentRepository {


    public EnrollmentRepositoryImpl(Session session) {
        super(session);
    }

    @Override
    public Class<Enrollment> getEntityClass() {
        return Enrollment.class;
    }


    @Override
    public List<Course> findEnrolledCoursesByStudent(Student student) {
        String jpql = "SELECT e.courseId FROM Enrollment e WHERE e.studentId = :studentId";
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Course> query = session.createQuery(jpql, Course.class);
        query.setParameter("student", student);
        List<Course> courses = query.list();
        session.close();
        return courses;
    }


    @Override
    public List<Enrollment> showAllEnrollments  () throws NoResultException {
        String hql = "FROM Enrollment";
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Enrollment> query = session.createQuery(hql, Enrollment.class);
        List<Enrollment> enrollments = query.list();
        session.close();
        return enrollments;
    }
    @Override
    public List<Enrollment> enrollmentCourseByCourseId(Long id) throws NoResultException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Enrollment> query = session.createQuery(" FROM Enrollment e WHERE e.studentId =:id",
                Enrollment.class);
        query.setParameter("id",id);
        List<Enrollment> enrollments = query.list();
        session.close();
        return enrollments;
    }

    @Override
    public boolean isEnrolled(Long studentId, Long courseId) {
        String jpql = "SELECT COUNT(e) FROM Enrollment e WHERE e.studentId = :studentId AND e.courseId = :courseId";
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Long> query = session.createQuery(jpql, Long.class);
        query.setParameter("studentId", studentId);
        query.setParameter("courseId", courseId);
        Long count = query.getSingleResult();
        session.close();
        return count > 0;
    }

    @Override
    public List<String> findEnrolledCourseNamesByStudent(Long studentId) {
        String jpql = "SELECT c.courseName " +
                "FROM Course c " +
                "JOIN Enrollment e ON c.id = e.courseId " +
                "WHERE e.studentId = :studentId";
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<String> query = session.createQuery(jpql, String.class);
        query.setParameter("studentId", studentId);
        List<String> courseNames = query.getResultList();
        session.close();
        return courseNames;
    }
}
