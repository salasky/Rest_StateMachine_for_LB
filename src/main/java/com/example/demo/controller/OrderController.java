package com.example.demo.controller;

import com.example.demo.domain.Order;
import com.example.demo.dto.OrderDTO;
import com.example.demo.service.OrderServiceS;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")

public class OrderController {

    @Autowired
    private OrderServiceS orderServiceS;


    @PostMapping("/new")
    public ResponseEntity newOrder(@RequestBody OrderDTO orderDTO){
        return orderServiceS.newOrder(orderDTO);
    }

    @GetMapping("/")
    public List<Order> getAll(){
        return orderServiceS.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return orderServiceS.getById(id);
    }

    @GetMapping("/perform/{id}")
    public ResponseEntity perform(@PathVariable Long id){
        return orderServiceS.performanceState(id);
    }

    @GetMapping("/control/{id}")
    public ResponseEntity control(@PathVariable Long id){
        return orderServiceS.control(id);
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity accept(@PathVariable Long id){
        return orderServiceS.accept(id);
    }

    @GetMapping("/revision/{id}")
    public ResponseEntity revision(@PathVariable Long id){
        return orderServiceS.revision(id);
    }

    @GetMapping("/secondperform/{id}")
    public ResponseEntity secondperform(@PathVariable Long id){
        return orderServiceS.secondPerform(id);
    }

}
