package com.springboot.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    // Custom query to find an employee by email
    @Query(value = "SELECT * FROM employee WHERE email = ?1", nativeQuery = true)
    List<Employee> findByEmailAddress(String email);

    // Fetch list of all the employees
    @Query(value = "SELECT * FROM employee", nativeQuery = true)
    List<Employee> findAllEmployees();

    // Custom query to find an employee by id
    @Query(value = "SELECT * FROM employee WHERE emp_id = ?1", nativeQuery = true)
    List<Employee> findByEmployeeId(int empId);

    // Get count of employees in the employee table
    @Query(value = "SELECT COUNT(emp_id) FROM employee", nativeQuery = true)
    int countAllEmployees();
}
