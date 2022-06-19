package com.example.demo.service;

import com.example.demo.statemachine.event.Event;
import com.example.demo.statemachine.state.State;
import org.springframework.statemachine.StateMachine;

public interface OrderServiceS {
    StateMachine<State,Event> FirstPerform(Long orderId);

    StateMachine<State,Event> control(Long orderId);

    StateMachine<State,Event>  accept(Long orderId);

    StateMachine<State,Event>  revision(Long orderId);

    StateMachine<State,Event> SecondPerform(Long orderId);

    StateMachine<State,Event>  internalSuccess(Long orderId);

    StateMachine<State,Event>  internalFailed(Long orderId);



    //получение конечного автомата из базы данных
    StateMachine<State, Event> build (Long orderId);
    //Конечный автомат поддерживает стандартные сообщения Spring.
    void senEvent(Long orderId,StateMachine<State,Event> sm,Event event);
}
