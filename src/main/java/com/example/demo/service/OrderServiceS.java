package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.dto.OrderDTO;
import com.example.demo.statemachine.event.Event;
import com.example.demo.statemachine.state.State;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;

import java.util.List;

public interface OrderServiceS {
    public ResponseEntity newOrder(OrderDTO orderDTO);

    ResponseEntity control(Long orderId);

    ResponseEntity  accept(Long orderId);

    ResponseEntity  revision(Long orderId);

    ResponseEntity secondPerform(Long orderId);

    ResponseEntity performanceState(Long orderid);

    ResponseEntity delete(Long id);
    List<Order> getAll();
    ResponseEntity getById(Long id);

    //получение конечного автомата из базы данных
    StateMachine<State, Event> build (Long orderId);
    //Конечный автомат поддерживает стандартные сообщения Spring.
    void senEvent(Long orderId,StateMachine<State,Event> sm,Event event);
}
