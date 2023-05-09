package com.springdata.jpa.controllers;

import com.springdata.jpa.entities.Student;
import com.springdata.jpa.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student savedStudent = studentService.createStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> studentList = studentService.getAllStudents();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable long id) {
        Student student = studentService.getStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/firstName/{firstName}/all")
    public ResponseEntity<List<Student>> getAllStudnetsByFirstName(@PathVariable String firstName) {
        List<Student> studentList = studentService.getStudentsByFirstName(firstName);
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
        Student student = studentService.getStudentByEmail(email);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/{email}/exist?")
    public ResponseEntity<Boolean> isStudentExistByEmail(@PathVariable String email) {
        boolean isExist = studentService.isStudentExistWithEmail(email);
        return new ResponseEntity<>(isExist, HttpStatus.OK);
    }

    @GetMapping("/age/{age}/top")
    public ResponseEntity<Student> getTopStudentByAge(@PathVariable int age) {
        Student student = studentService.getTopStudentByAge(age);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/age/{age}/all")
    public ResponseEntity<List<Student>> getStuentsByAgeAscendingOrderByEmail(@PathVariable int age) {
        List<Student> studentList = studentService.getStuentsByAgeAscendingOrderByEmail(age);
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @GetMapping("/age/{age}/greater-or-equal")
    public ResponseEntity<List<Student>> getStudentsByAgeGreaterThanEqualGivenAge(@PathVariable int age) {
        List<Student> studentList = studentService.getStudentsByAgeGreaterThanEqualGivenAge(age);
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @PutMapping("/id/{id}/update")
    public ResponseEntity<Student> updateStudent(@PathVariable long id, @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(id, student);
        return new ResponseEntity<>(updatedStudent, updatedStudent != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}/delete")
    public ResponseEntity<String> deleteStudent(@PathVariable long id) {
        boolean isDeleted = studentService.deleteStudent(id);
        return new ResponseEntity<>(isDeleted ? "Student is deleted." : "Student not found!", isDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }


}
