/**
 @author: Paurav Shah
 @date: 31/05/2022

 @Repository annotation indicates that the class provides the mechanism for storage, retrieval, update, delete and
 search operation on objects.
 @Query annotation refers to the custom SQL database query for a given method.
 ?1, ?2, ... are the parameters passed into the query from the below method.
 */

package com.springboot.employee.repository;

import com.springboot.employee.entity.Employee;
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
