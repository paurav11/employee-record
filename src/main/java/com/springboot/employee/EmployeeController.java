package com.springboot.employee;

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

    // Redirect to index page
    @RequestMapping(value ="/", method = RequestMethod.GET)
    public RedirectView redirectToIndex() {
        return new RedirectView("http://localhost:8080/employees");
    }

    // Load index page with employee list
    @RequestMapping(value ="/employees", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

//    // Add a new employee
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

//    // Add a new employee
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

    // Add a new employee
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

//    // Find employees by their email
//    @RequestMapping(value = "/employees/{email}", method = RequestMethod.GET)
//    public @ResponseBody boolean isEmployee(@PathVariable String email){
//        List<Employee> empList = employeeRepository.findByEmailAddress(email);
//        if (empList.size() != 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    // Find an employee by email
    private boolean isEmployee(String email){
        List<Employee> empList = employeeRepository.findByEmailAddress(email);
        if (empList.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    // Get a list of all the employees
    @RequestMapping(value = "/employees/all", method = RequestMethod.GET)
    public @ResponseBody List<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployees();
    }

    // Find an employee by id
    private boolean isEmployeeId(int id){
        List<Employee> empList = employeeRepository.findByEmployeeId(id);
        if (empList.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    // Delete an employee
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

    // Count total number of employee records
    @RequestMapping(value = "/employees/count", method = RequestMethod.GET)
    public @ResponseBody int getCountOfAllEmployees(){
        return employeeRepository.countAllEmployees();
    }

    // Fetch employee details by id
    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
    public @ResponseBody List<Employee> getEmployee(@PathVariable int id){
        return employeeRepository.findByEmployeeId(id);
    }

    // Update an employee details
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
