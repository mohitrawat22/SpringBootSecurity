package com.mor.sbsecurity.controller;

import com.mor.sbsecurity.model.Employee;
import com.mor.sbsecurity.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("/hello")
    public String helloEmployee() {
        //System.out.println("Hello employee ...");
        return "Hello Employee";
    }

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody Employee employee) throws Exception {
        String jwtTokenString = employeeService.getAuthenticationToken(employee);
        return jwtTokenString;
    }

    @PostMapping("/signup")
    public Employee signup(@RequestBody Employee employee) throws Exception {
        Employee employeeResponse = employeeService.saveEmployee(employee);
        return employeeResponse;
    }

}
