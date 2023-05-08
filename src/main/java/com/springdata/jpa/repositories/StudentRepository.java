package com.springdata.jpa.repositories;

import com.springdata.jpa.entities.Student;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByFirstName(String firstName);

    Optional<Student> findByAge(int age);

    boolean existByEmail(String email);

    List<Student> findAllByAgeGreaterThanEqual(int age);

}
