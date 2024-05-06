package org.example.service.employee;

import jakarta.persistence.NoResultException;
import org.example.base.service.BaseService;
import org.example.model.user.Employee;

public interface EmployeeService extends BaseService<Employee, Long> {

    Employee findByFullName(String firstName, String lastName) throws NoResultException;

}
