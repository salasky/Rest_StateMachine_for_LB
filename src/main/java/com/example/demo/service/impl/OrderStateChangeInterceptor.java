package com.example.demo.service.impl;

import com.example.demo.domain.Order;
import com.example.demo.repositories.OrderRepositories;
import com.example.demo.statemachine.event.Event;
import com.example.demo.statemachine.state.State;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OrderStateChangeInterceptor extends StateMachineInterceptorAdapter<State, Event> {

   @Autowired
   private final OrderRepositories orderRepositories;

    @Override
    public void preStateChange(org.springframework.statemachine.state.State<State, Event> state, Message<Event> message, Transition<State, Event> transition, StateMachine<State, Event> stateMachine, StateMachine<State, Event> rootStateMachine) {
        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(OrderServiceSimpl.PAYMENT_ID_HEADER, -1L)))
                    .ifPresent(orderId -> {
                        Order order = orderRepositories.getOne(orderId);
                        order.setState(state.getId());
                        orderRepositories.save(order);
                    });
        });
    }

}
