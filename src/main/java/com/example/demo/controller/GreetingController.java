package com.example.demo.controller;

import com.example.demo.config.JwtTokenRepository;
import com.example.demo.domain.Employee;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class GreetingController {

    @Autowired
    private UserService userService;





    @GetMapping("/users")
    public List<User> greeting() {

        return userService.getAll();
    }



}
