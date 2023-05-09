package com.springdata.jpa.services;

import com.springdata.jpa.entities.Student;
import com.springdata.jpa.exceptions.NotFoundException;
import com.springdata.jpa.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(long id) {
        return studentRepository.findById(id).orElseThrow(()-> new NotFoundException(Student.class));
    }

    public List<Student> getStudentsByFirstName(String firstName) {
        return studentRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email).orElseThrow(()-> new NotFoundException(Student.class));
    }

    public boolean isStudentExistWithEmail(String email) {
        return studentRepository.existsStudentByEmail(email);
    }

    public Student getTopStudentByAge(int age) {
        return studentRepository.findTopByAge(age).orElseThrow(()-> new NotFoundException(Student.class));
    }

    public List<Student> getStuentsByAgeAscendingOrderByEmail(int age) {
        return studentRepository.findAllByAgeOrderByEmailAsc(age);
    }

    public List<Student> getStudentsByAgeGreaterThanEqualGivenAge(int age) {
        return studentRepository.findAllByAgeGreaterThanEqual(age);
    }

    public Student updateStudent(long id, Student student) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(()-> new NotFoundException(Student.class));
        if(existingStudent != null){
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setAge(student.getAge());
        }
        return existingStudent;
    }

    public void deleteStudent(long id) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new NotFoundException(Student.class));
        studentRepository.delete(student);
    }

}
