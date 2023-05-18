package com.springboot.starter.services;

import com.springboot.starter.constants.AppUtils;
import com.springboot.starter.entities.Student;
import com.springboot.starter.exceptions.NotFoundException;
import com.springboot.starter.models.PaginationArgs;
import com.springboot.starter.repositories.StudentRepository;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    public Specification<Student> getStudentSpecification(Map<String, Object> specParameters) {
        return Specification.where(((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, Object> entry : specParameters.entrySet()) {
                String filterBy = entry.getKey();
                String filterWith = entry.getValue().toString();

                if (filterWith != null && !filterWith.isEmpty()) {
                    Class<?> type = root.get(filterBy).getJavaType();
                    if (type.equals(Long.class)) {
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), Long.valueOf(filterWith)));
                    } else if (type.equals(Integer.class)) {
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), Integer.valueOf(filterWith)));
                    } else if (type.equals(String.class)) {
                        predicates.add(criteriaBuilder.like(
                                criteriaBuilder.upper(root.get(filterBy)), "%" + filterWith.toUpperCase() + "%"));
                    } else {
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), filterWith));
                    }
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
        }));
    }

    public Page<Student> getPaginatedStudents(PaginationArgs paginationArgs) {
        Pageable pageable = AppUtils.getPageable(paginationArgs);

        Map<String, Object> specParameters = AppUtils.getParameters(paginationArgs.getParameters());
        if (!specParameters.isEmpty()) {
            return studentRepository.findAll(getStudentSpecification(specParameters), pageable);
        }

        return studentRepository.findAll(pageable);
    }

    public Student getStudentById(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new NotFoundException(Student.class));
    }

    public List<Student> getStudentsByFirstName(String firstName) {
        return studentRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(Student.class));
    }

    public boolean isStudentExistWithEmail(String email) {
        return studentRepository.existsStudentByEmail(email);
    }

    public Student getTopStudentByAge(int age) {
        return studentRepository.findTopByAge(age).orElseThrow(() -> new NotFoundException(Student.class));
    }

    public List<Student> getStuentsByAgeAscendingOrderByEmail(int age) {
        return studentRepository.findAllByAgeOrderByEmailAsc(age);
    }

    public List<Student> getStudentsByAgeGreaterThanEqualGivenAge(int age) {
        return studentRepository.findAllByAgeGreaterThanEqual(age);
    }

    public Student updateStudent(long id, Student student) {
        Student existingStudent =
                studentRepository.findById(id).orElseThrow(() -> new NotFoundException(Student.class));
        if (existingStudent != null) {
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setAge(student.getAge());
            studentRepository.save(existingStudent);
        }
        return existingStudent;
    }

    public void deleteStudent(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(Student.class));
        studentRepository.delete(student);
    }
}
