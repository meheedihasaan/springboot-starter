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

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByFirstName(String firstName) {
        return studentRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email).orElse(null);
    }

    public boolean isStudentExistWithEmail(String email) {
        return studentRepository.existsStudentByEmail(email);
    }

    public Student getTopStudentByAge(int age) {
        return studentRepository.findTopByAge(age).orElse(null);
    }

    public List<Student> getStuentsByAgeAscendingOrderByEmail(int age) {
        return studentRepository.findAllByAgeOrderByEmailAsc(age);
    }

    public List<Student> getStudentsByAgeGreaterThanEqualGivenAge(int age) {
        return studentRepository.findAllByAgeGreaterThanEqual(age);
    }

    public Student updateStudent(long id, Student student) {
        Student existingStudent = studentRepository.findById(id).orElse(null);
        if(existingStudent != null){
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setAge(student.getAge());
        }
        return existingStudent;
    }

    public boolean deleteStudent(long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            studentRepository.delete(studentOptional.get());
            return true;
        }
        return false;
    }

}
