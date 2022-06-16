package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")

public class OrderController {

    @GetMapping("/")
    public String getOrder(){
        return "index";
    }

    @PostMapping("/")
    public void setOrder(){

    }



}
