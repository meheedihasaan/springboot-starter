package com.springdata.jpa.repositories;

import com.springdata.jpa.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Page<Student> findAll(Specification specification, Pageable pageable);

    List<Student> findByFirstNameContainingIgnoreCase(String firstName);

    Optional<Student> findByEmail(String email);

    boolean existsStudentByEmail(String email);

    Optional<Student> findTopByAge(int age);

    List<Student> findAllByAgeOrderByEmailAsc(int age);

    List<Student> findAllByAgeGreaterThanEqual(int age);

}
