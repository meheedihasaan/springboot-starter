package com.springboot.starter.controllers;

import com.springboot.starter.entities.Student;
import com.springboot.starter.enums.AscOrDescType;
import com.springboot.starter.models.PaginationArgs;
import com.springboot.starter.models.Response;
import com.springboot.starter.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.springboot.starter.constants.AppConstant.*;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<Response> createStudent(@RequestBody Student student) {
        return Response.getResponseEntity(true, "Student is created.", studentService.createStudent(student));
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllStudents() {
        return Response.getResponseEntity(true, "Data loaded successfully.", studentService.getAllStudents());
    }

    @GetMapping("/all/paginated")
    public ResponseEntity<Response> getPaginatedStudents(
            @RequestParam(name = PAGE_NUMBER, defaultValue = "0") int pageNumber,
            @RequestParam(name = PAGE_SIZE, defaultValue = "20") int pageSize,
            @RequestParam(name = SORT_BY, defaultValue = "") String sortBy,
            @RequestParam(name = ASC_OR_DESC_TYPE, defaultValue = "") AscOrDescType ascOrDesc,
            @RequestParam(required = false) Map<String, Object> parameters
            ) {
        PaginationArgs paginationArgs = new PaginationArgs(pageNumber, pageSize, sortBy, ascOrDesc, parameters);
        return Response.getResponseEntity(true, "Data loaded successfully.", studentService.getPaginatedStudents(paginationArgs));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Response> getStudentById(@PathVariable long id) {
        return Response.getResponseEntity(true, "Data loaded successfully.", studentService.getStudentById(id));
    }

    @GetMapping("/firstName/{firstName}/all")
    public ResponseEntity<Response> getAllStudnetsByFirstName(@PathVariable String firstName) {
        return Response.getResponseEntity(true, "Data loaded successfully.", studentService.getStudentsByFirstName(firstName));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Response> getStudentByEmail(@PathVariable String email) {
        return Response.getResponseEntity(true, "Data loaded successfully.", studentService.getStudentByEmail(email));
    }

    @GetMapping("/{email}/exist?")
    public ResponseEntity<Response> isStudentExistByEmail(@PathVariable String email) {
        boolean isExist = studentService.isStudentExistWithEmail(email);
        return Response.getResponseEntity(true, isExist ? "Student exists with email "+email+"." : "Student doesn't exist with email "+email+".");
    }

    @GetMapping("/age/{age}/top")
    public ResponseEntity<Response> getTopStudentByAge(@PathVariable int age) {
        return Response.getResponseEntity(true, "Data loaded successfully.", studentService.getTopStudentByAge(age));
    }

    @GetMapping("/age/{age}/all")
    public ResponseEntity<Response> getStuentsByAgeAscendingOrderByEmail(@PathVariable int age) {
        return Response.getResponseEntity(true, "Data loaded successfully.", studentService.getStuentsByAgeAscendingOrderByEmail(age));
    }

    @GetMapping("/age/{age}/greater-or-equal")
    public ResponseEntity<Response> getStudentsByAgeGreaterThanEqualGivenAge(@PathVariable int age) {
        return Response.getResponseEntity(true, "Data loaded successfully.", studentService.getStudentsByAgeGreaterThanEqualGivenAge(age));
    }

    @PutMapping("/id/{id}/update")
    public ResponseEntity<Response> updateStudent(@PathVariable long id, @RequestBody Student student) {
        return Response.getResponseEntity(true, "Student is updated.", studentService.updateStudent(id, student));
    }

    @DeleteMapping("/id/{id}/delete")
    public ResponseEntity<Response> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return Response.getResponseEntity(true, "Student is deleted.");
    }


}
