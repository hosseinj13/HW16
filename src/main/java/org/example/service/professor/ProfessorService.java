package org.example.service.professor;

import jakarta.persistence.NoResultException;
import org.example.base.service.BaseService;
import org.example.model.user.Professor;

public interface ProfessorService extends BaseService<Professor, Long> {

    Professor findBySemester(String semester);
    Professor findByFullName(String firstName, String lastName) throws NoResultException;

}
