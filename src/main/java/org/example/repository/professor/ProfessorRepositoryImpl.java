package org.example.repository.professor;

import jakarta.persistence.NoResultException;
import org.example.base.repository.BaseRepositoryImpl;
import org.example.conncetion.SessionFactorySingleton;
import org.example.model.user.Professor;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ProfessorRepositoryImpl extends BaseRepositoryImpl<Professor, Long> implements ProfessorRepository {

    public ProfessorRepositoryImpl(Session session){
        super(session);
    }

    @Override
    public Class<Professor> getEntityClass() {
        return Professor.class;
    }

    @Override
    public Professor findBySemester(String semester) {
        return session.createQuery(
                        "SELECT p FROM Professor p WHERE p.semester = :semester", Professor.class)
                .setParameter("semester", semester)
                .getSingleResult();
    }

    @Override
    public Professor findByFullName(String firstName, String lastName) throws NoResultException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Professor> query = session.createQuery("FROM Professor t WHERE t.firstName = :firstName AND t.lastName = :lastName",
                Professor.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        Professor teacher = query.uniqueResult();
        session.close();
        return teacher;
    }
}
