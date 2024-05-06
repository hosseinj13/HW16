package org.example.repository.course;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.base.repository.BaseRepositoryImpl;
import org.example.conncetion.SessionFactorySingleton;
import org.example.model.Course;
import org.example.model.user.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CourseRepositoryImpl extends BaseRepositoryImpl<Course, Long> implements CourseRepository {

    public CourseRepositoryImpl(Session session) {
        super(session);
    }


    @Override
    public Class<Course> getEntityClass() {
        return Course.class;
    }




    @Override
    public List<Course> findAvailableCourses() {
        String jpql = "SELECT c FROM Course c";
        TypedQuery<Course> query = session.createQuery(jpql, Course.class);
        return query.getResultList();
    }

    @Override
    public Course findByCourseName(String courseName) throws NoResultException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Course> query = session.createQuery("FROM Course c WHERE c.courseName =:courseName", Course.class);
        query.setParameter("courseName", courseName);
        Course course = query.getSingleResult();
        session.close();
        return course;
    }
}
