package com.springdata.jpa.repositories;

import com.springdata.jpa.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFirstNameContainingIgnoreCase(String firstName);

    Optional<Student> findByEmail(String email);

    boolean existByEmail(String email);

    List<Student> findAllByAgeOrderByAgeAsc(int age);

    List<Student> findAllByAgeGreaterThanEqual(int age);

}
