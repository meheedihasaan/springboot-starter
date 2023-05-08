package com.springdata.jpa.entities;

import jakarta.persistence.*;

@Entity
@Table(
        name = "STUDENT",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_email_unique",
                        columnNames = "EMAIL"
                )
        }
)
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_seq",
            sequenceName = "STUDENT_SEQUENCE",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_seq"
    )
    @Column(
            name = "ID",
            updatable = false
    )
    private long id;

    @Column(
            name = "FIRST_NAME",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "LAST_NAME",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "EMAIL",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "AGE",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private int age;

    public Student() {

    }

    public Student(long id, String firstName, String lastName, String email, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName(String firstName) {
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
