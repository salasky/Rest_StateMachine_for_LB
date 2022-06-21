package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.dto.OrderDTO;
import com.example.demo.statemachine.event.Event;
import com.example.demo.statemachine.state.State;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;

public interface OrderServiceS {
    StateMachine<State,Event> start(Long orderId);

    StateMachine<State,Event> control(Long orderId);

    StateMachine<State,Event>  accept(Long orderId);

    StateMachine<State,Event>  revision(Long orderId);

    StateMachine<State,Event> SecondPerform(Long orderId);

    StateMachine<State,Event>  internalSuccess(Long orderId);

    StateMachine<State,Event>  internalFailed(Long orderId);



    ResponseEntity newOrder(OrderDTO orderDTO);


    //получение конечного автомата из базы данных
    StateMachine<State, Event> build (Long orderId);
    //Конечный автомат поддерживает стандартные сообщения Spring.
    void senEvent(Long orderId,StateMachine<State,Event> sm,Event event);
}
