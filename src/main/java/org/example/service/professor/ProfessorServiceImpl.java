package org.example.service.professor;

import jakarta.persistence.NoResultException;
import org.example.base.service.BaseServiceImpl;
import org.example.model.user.Professor;
import org.example.enums.ProfessorType;
import org.example.repository.professor.ProfessorRepository;
import org.hibernate.SessionFactory;

import java.text.DecimalFormat;

public class ProfessorServiceImpl extends BaseServiceImpl<Professor, Long, ProfessorRepository> implements ProfessorService {

    public ProfessorServiceImpl(ProfessorRepository repository){
        super(repository);
    }

    @Override
    public Professor findBySemester(String semester) {
        return repository.findBySemester(semester);
    }

    @Override
    public Professor findByFullName(String firstName, String lastName) throws NoResultException {
        return repository.findByFullName(firstName, lastName);
    }
}
