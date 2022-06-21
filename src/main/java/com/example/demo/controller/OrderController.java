package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.service.OrderServiceS;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")

public class OrderController {

    @Autowired
    private OrderServiceS orderServiceS;

    @GetMapping("/")
    public String getOrder(){
        return "index";
    }

    @PostMapping("/new")
    public ResponseEntity newOrder(@RequestBody OrderDTO orderDTO){
        return orderServiceS.newOrder(orderDTO);
    }




}
