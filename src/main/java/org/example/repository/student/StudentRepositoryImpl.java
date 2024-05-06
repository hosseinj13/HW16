package org.example.repository.student;

import jakarta.persistence.NoResultException;
import jakarta.validation.ConstraintViolationException;
import org.example.base.repository.BaseRepositoryImpl;
import org.example.conncetion.SessionFactorySingleton;
import org.example.model.Course;
import org.example.model.Grade;
import org.example.model.user.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class StudentRepositoryImpl extends BaseRepositoryImpl<Student, Long> implements StudentRepository {


    public StudentRepositoryImpl(Session session) {
        super(session);
    }

    @Override
    public Class<Student> getEntityClass() {
        return Student.class;
    }

    @Override
    public Student findByFullName(String firstName, String lastName) throws NoResultException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Student> query = session.createQuery("FROM Student s WHERE s.firstName = :firstName AND s.lastName = :lastName",
                Student.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName",lastName);
        Student student = query.uniqueResult();
        session.close();
        return student;
    }
}
