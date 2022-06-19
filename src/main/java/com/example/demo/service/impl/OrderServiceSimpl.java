package com.example.demo.service.impl;

import com.example.demo.domain.User;
import com.example.demo.service.OrderServiceS;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceSimpl implements OrderServiceS {

    @Autowired
    private UserService userService;


/*
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    User user=userService.getByLogin(currentPrincipalName);

*/

    @Override
    public boolean perform(String userId, String orderId) {
        return false;
    }

    @Override
    public boolean control(String orderId) {
        return false;
    }

    @Override
    public boolean accept(String orderId) {
        return false;
    }

    @Override
    public boolean revision(String orderId) {
        return false;
    }

    @Override
    public boolean secondControl(String orderId) {
        return false;
    }


}
