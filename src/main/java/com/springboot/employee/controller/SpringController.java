package com.springboot.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// Controller to control the HTTP Requests sent by the user
@Controller
public class SpringController {

    @RequestMapping(value = {"/","/employees"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
