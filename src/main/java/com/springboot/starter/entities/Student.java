package com.springboot.starter.entities;

import com.springboot.starter.constants.AppTables;
import com.springboot.starter.models.AuditModel;
import jakarta.persistence.*;

@Entity
@Table(name = AppTables.STUDENT_TABLE)
public class Student extends AuditModel<String> {

    @Column(name = AppTables.StudentTable.FIRST_NAME)
    private String firstName;

    @Column(name = AppTables.StudentTable.LAST_NAME)
    private String lastName;

    @Column(name = AppTables.StudentTable.EMAIL, nullable = false, unique = true)
    private String email;

    @Column(name = AppTables.StudentTable.AGE)
    private int age;

    public Student() {}

    public Student(String firstName, String lastName, String email, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
