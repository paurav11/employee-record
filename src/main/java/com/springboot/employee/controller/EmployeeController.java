/**
 @author: Paurav Shah
 @date: 31/05/2022

 @Controller annotation indicates that a particular class serves the role of a controller.
 @Autowired annotation enables you to inject the object dependency implicitly.
 @RequestMapping annotation maps a specific request path or pattern onto a controller.
 ResponseEntity represents the whole HTTP response: status code, headers, and body.
 */

package com.springboot.employee.controller;

import com.springboot.employee.entity.Employee;
import com.springboot.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

// Controller to control the HTTP Requests sent by the user
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Method to redirect to the index page
    @RequestMapping(value ="/", method = RequestMethod.GET)
    public RedirectView redirectToIndex() {
        return new RedirectView("http://localhost:8080/employees");
    }

    // Method to load index page having an employee list
    @RequestMapping(value ="/employees", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

//    // Method to add a new employee
//    @RequestMapping(value = "/employees/add", method = RequestMethod.POST)
//    public @ResponseBody String addNewEmployee(@RequestParam("firstName") String firstName,
//                                               @RequestParam("lastName") String lastName,
//                                               @RequestParam("email") String email,
//                                               @RequestParam("dob") String dob,
//                                               @RequestParam("salary") Double salary,
//                                               @RequestParam("gender") String gender,
//                                               @RequestParam("status") String status) {
//
//        // Save form data to the database
//        try {
//            Employee emp = new Employee(firstName,lastName,email,gender,dob,salary,status);
//            employeeRepository.save(emp);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return "Employee added successfully.";
//    }

//    // Method to add a new employee
//    @RequestMapping(value = "/employees/add", method = RequestMethod.POST)
//    public @ResponseBody RedirectView addNewEmployee(@RequestParam Map<String,String> body) {
//
//        // Get form data from Request Body
//        String firstName = StringUtils.capitalize(body.get("firstName").trim());
//        String lastName = StringUtils.capitalize(body.get("lastName").trim());
//        String email = body.get("email").toLowerCase().trim();
//        String gender = body.get("gender").trim();
//        String dob = body.get("dob").trim();
//        double salary = Double.parseDouble(body.get("salary").trim());
//        String status = body.get("status").trim();
//
//        // Save form data to the database
//        try {
//            Employee emp = new Employee(firstName,lastName,email,gender,dob,salary,status);
//            employeeRepository.save(emp);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return new RedirectView("http://localhost:8080/employees");
//    }

    // Method to add a new employee
    @RequestMapping(value = "/employees/add", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity addNewEmployee(@RequestBody Map<String,String> body) {

        if (isEmployee(body.get("email"))) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        } else {
            String firstName = StringUtils.capitalize(body.get("firstName").trim());
            String lastName = StringUtils.capitalize(body.get("lastName").trim());
            String email = body.get("email").trim();
            String gender = body.get("gender").trim();
            String dob = body.get("dob").trim();
            double salary = Double.parseDouble(body.get("salary").trim());
            String status = body.get("status").trim();

            // Save form data to the database
            try {
                Employee emp = new Employee(firstName,lastName,email,gender,dob,salary,status);
                employeeRepository.save(emp);
            } catch (Exception e) {
                return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
            }

            return new ResponseEntity(HttpStatus.CREATED);
        }
    }

//    // Method to find an employee by email
//    @RequestMapping(value = "/employees/{email}", method = RequestMethod.GET)
//    public @ResponseBody boolean isEmployee(@PathVariable String email){
//        List<Employee> empList = employeeRepository.findByEmailAddress(email);
//        if (empList.size() != 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    // Method to find an employee by email
    private boolean isEmployee(String email){
        List<Employee> empList = employeeRepository.findByEmailAddress(email);
        if (empList.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    // Method to get a list of all the employees
    @RequestMapping(value = "/employees/all", method = RequestMethod.GET)
    public @ResponseBody List<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployees();
    }

    // Method to find an employee by id
    private boolean isEmployeeId(int id){
        List<Employee> empList = employeeRepository.findByEmployeeId(id);
        if (empList.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    // Method to delete an employee
    @RequestMapping(value = "/employees/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity deleteAnEmployee(@PathVariable String id){
        int empId = Integer.parseInt(id);
        if (isEmployeeId(empId)) {
            employeeRepository.deleteById(empId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // Method to count the total number of employees
    @RequestMapping(value = "/employees/count", method = RequestMethod.GET)
    public @ResponseBody int getCountOfAllEmployees(){
        return employeeRepository.countAllEmployees();
    }

    // Method to fetch an employee by id
    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
    public @ResponseBody List<Employee> getEmployee(@PathVariable int id){
        return employeeRepository.findByEmployeeId(id);
    }

    // Method to update an employee
    @RequestMapping(value = "/employees/edit/{id}", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity updateEmployee(@RequestBody Map<String,String> body, @PathVariable int id) {

        String firstName = StringUtils.capitalize(body.get("firstName").trim());
        String lastName = StringUtils.capitalize(body.get("lastName").trim());
        String email = body.get("email").trim();
        String gender = body.get("gender").trim();
        String dob = body.get("dob").trim();
        double salary = Double.parseDouble(body.get("salary").trim());
        String status = body.get("status").trim();

        // Update form data in the database
        try {
            Employee emp = new Employee();
            emp.setEmployeeId(id);
            emp.setFirstName(firstName);
            emp.setLastName(lastName);
            emp.setEmail(email);
            emp.setGender(gender);
            emp.setDob(dob);
            emp.setAge(dob);
            emp.setSalary(salary);
            emp.setStatus(status);
            employeeRepository.save(emp);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
