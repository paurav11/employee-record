package com.springboot.employee.model;

import java.time.LocalDate;
import java.time.Period;

// Employee Model for the Employee Table in the Database Schema
public class Employee {
    private int employeeId;
    private String employeeName;
    private String emailId;
    private String dob;
    private int age;
    private double salary;
    private boolean status;

    public Employee(String firstName, String lastName, String emailId, String dob, double salary, boolean status) {
        this.employeeId = (int)(Math.random()*(1000000 - 1 + 1) + 1);
        this.employeeName = firstName + " " + lastName;
        this.emailId = emailId;
        this.dob = dob;
        this.age = calculateAge(dob);
        this.salary = salary;
        this.status = status;
    }

    private int calculateAge(String dob) {
        LocalDate dobLocal = LocalDate.parse(dob);
        LocalDate currDate = LocalDate.now();
        return Period.between(dobLocal,currDate).getYears();
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
