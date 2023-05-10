package com.springdata.jpa.entities;

import com.springdata.jpa.constants.AppTables;
import com.springdata.jpa.constants.AppTables.StudentTable;
import com.springdata.jpa.models.AuditModel;
import jakarta.persistence.*;

@Entity
@Table(name = AppTables.STUDENT)
public class Student extends AuditModel<String> {

    @Column(name = StudentTable.FIRST_NAME)
    private String firstName;

    @Column(name = StudentTable.LAST_NAME)
    private String lastName;

    @Column(name = StudentTable.EMAIL, nullable = false, unique = true)
    private String email;

    @Column(name = StudentTable.AGE)
    private int age;

    public Student() {

    }

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
