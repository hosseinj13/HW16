package org.example.repository.employee;

import jakarta.persistence.NoResultException;
import org.example.base.repository.BaseRepository;
import org.example.model.user.Employee;
import org.example.model.user.Professor;

import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<Employee, Long> {

    Employee findByFullName(String firstName, String lastName) throws NoResultException;


}
