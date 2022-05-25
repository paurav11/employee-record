package com.springboot.employee;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

// Employee Model for the Employee Table in the Database Schema
// Entity Class
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "emp_id")
    private int employeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "dob")
    private String dob;

    @Column(name = "age")
    private int age;

    @Column(name = "salary")
    private double salary;

    @Column(name = "status")
    private String status;

    public Employee() {
    }

    public Employee(String firstName, String lastName, String email, String gender, String dob, double salary, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.age = calculateAge(dob);
        LocalDate tempDate = LocalDate.parse(dob);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.dob = dateTimeFormatter.format(tempDate);
        this.salary = salary;
        this.status = status;
    }

    private int calculateAge(String dob) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dobLocal = LocalDate.parse(dateTimeFormatter.format(LocalDate.parse(dob)));
        LocalDate currDate = LocalDate.now();
        return Period.between(dobLocal,currDate).getYears();
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setAge(String dob) {
        this.age = calculateAge(dob);
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
