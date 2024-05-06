package org.example.repository.employee;

import jakarta.persistence.NoResultException;
import org.example.base.repository.BaseRepositoryImpl;
import org.example.conncetion.SessionFactorySingleton;
import org.example.model.user.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

public class EmployeeRepositoryImpl extends BaseRepositoryImpl<Employee, Long> implements EmployeeRepository {

    public EmployeeRepositoryImpl(Session session){
        super(session);
    }


    @Override
    public Class<Employee> getEntityClass() {
        return Employee.class;
    }


    @Override
    public Employee findByFullName(String firstName, String lastName) throws NoResultException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Employee> query = session.createQuery("FROM Employee e WHERE e.firstName = :firstName AND e.lastName = :lastName",
                Employee.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        Employee employee = query.uniqueResult();
        session.close();
        return employee;
    }
}
