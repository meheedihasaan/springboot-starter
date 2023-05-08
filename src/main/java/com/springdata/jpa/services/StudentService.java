package com.springdata.jpa.services;

import com.springdata.jpa.entities.Student;
import com.springdata.jpa.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void createStudent(Student student) {
        studentRepository.save(student);
    }

    public Student getStudentById(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getStudentsByFirstName(String firstName) {
        return studentRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email).orElse(null);
    }

    public boolean isStudentExistWithEmail(String email) {
        return studentRepository.existByEmail(email);
    }

    public List<Student> getStuentsByAgeAscendingOrder(int age) {
        return studentRepository.findAllByAgeOrderByAgeAsc(age);
    }

    public List<Student> getStudentsByAgeGreaterThanGivenAge(int age) {
        return studentRepository.findAllByAgeGreaterThanEqual(age);
    }

    public void deleteStudent(long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            studentRepository.delete(studentOptional.get());
        }
    }

}
