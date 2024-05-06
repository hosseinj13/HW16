package org.example.service.employee;

import jakarta.persistence.NoResultException;
import org.example.base.service.BaseServiceImpl;
import org.example.model.user.Employee;
import org.example.repository.employee.EmployeeRepository;
import org.hibernate.SessionFactory;

import java.text.DecimalFormat;

public class EmployeeServiceImpl extends BaseServiceImpl<Employee, Long, EmployeeRepository> implements EmployeeService {
    public EmployeeServiceImpl(EmployeeRepository repository) {
        super(repository);
    }

    @Override
    public Employee findByFullName(String firstName, String lastName) throws NoResultException {
        return repository.findByFullName(firstName, lastName);
    }

}

