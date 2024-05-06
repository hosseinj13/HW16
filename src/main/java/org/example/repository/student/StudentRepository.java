package org.example.repository.student;

import jakarta.persistence.NoResultException;
import org.example.base.repository.BaseRepository;
import org.example.model.user.Student;

public interface StudentRepository extends BaseRepository<Student, Long> {

    Student findByFullName(String firstName, String lastName) throws NoResultException;


}
