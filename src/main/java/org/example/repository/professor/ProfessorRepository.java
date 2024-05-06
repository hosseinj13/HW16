package org.example.repository.professor;

import jakarta.persistence.NoResultException;
import org.example.base.repository.BaseRepository;
import org.example.model.user.Professor;

public interface ProfessorRepository extends BaseRepository<Professor, Long> {

    Professor findBySemester(String semester);
    Professor findByFullName(String firstName, String lastName) throws NoResultException;

}
