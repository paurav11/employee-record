package com.springboot.employee;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.util.Map;

// Controller to control the HTTP Requests sent by the user
@Controller
public class SpringController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public RedirectView redirectToIndex() {
        return new RedirectView("http://localhost:8080/employees");
    }

    @RequestMapping(value ="/employees", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

//    // Add new employee request
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

    // Add new employee request
    @RequestMapping(value = "/employees/add", method = RequestMethod.POST)
    public @ResponseBody RedirectView addNewEmployee(@RequestParam Map<String,String> body) {

        // Get form data from Request Body
        String firstName = StringUtils.capitalize(body.get("firstName"));
        String lastName = StringUtils.capitalize(body.get("lastName"));
        String email = body.get("email").toLowerCase();
        String dob = body.get("dob");
        double salary = Double.parseDouble(body.get("salary"));
        String gender = body.get("gender");
        String status = body.get("status");

        // Save form data to the database
        try {
            Employee emp = new Employee(firstName,lastName,email,gender,dob,salary,status);
            employeeRepository.save(emp);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new RedirectView("http://localhost:8080/employees");
    }

    // Get a list of all the employees
    @RequestMapping(value = "/employees/all", method = RequestMethod.GET)
    public @ResponseBody Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
