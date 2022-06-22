package com.example.demo.controller;

import com.example.demo.domain.Employee;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;
import com.example.demo.repositories.SubdivisionRepositories;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeController {

    private EmployeeService employeeService;
    private UserService userService;
    private SubdivisionRepositories subdivisionRepositories;

    public EmployeController(EmployeeService employeeService, UserService userService, SubdivisionRepositories subdivisionRepositories) {
        this.employeeService = employeeService;
        this.userService = userService;
        this.subdivisionRepositories = subdivisionRepositories;
    }

    @PostMapping("/add")
    private ResponseEntity addEmployeesAndUser(@RequestBody UserDTO userDTO) {
            return employeeService.add(userDTO);
    }
    //Удаление..изменение..

}
