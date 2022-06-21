package com.example.demo.service.impl;

import com.example.demo.domain.Order;
import com.example.demo.repositories.OrderRepositories;
import com.example.demo.service.OrderServiceS;
import com.example.demo.service.UserService;
import com.example.demo.statemachine.event.Event;
import com.example.demo.statemachine.state.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceSimpl implements OrderServiceS {


    private UserService userService;
    private StateMachineFactory<State, Event> stateMachineFactory;
    private OrderRepositories orderRepositories;

    @Autowired
    public OrderServiceSimpl(UserService userService, StateMachineFactory<State, Event> stateMachineFactory, OrderRepositories orderRepositories) {
        this.userService = userService;
        this.stateMachineFactory = stateMachineFactory;
        this.orderRepositories = orderRepositories;
    }
    
    /*
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    User user=userService.getByLogin(currentPrincipalName);

*/




    @Override
    public StateMachine<State, Event> FirstPerform (Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.START);
        return sm;
    }

    @Override
    public StateMachine<State, Event> control(Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.FIRST_CONTROL);
        return sm;
    }

    @Override
    public StateMachine<State, Event> accept(Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.SUCCESS);
        return sm;
    }

    @Override
    public StateMachine<State, Event> revision(Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.FAIL_CONTROL);
        return sm;
    }


    @Override
    public StateMachine<State, Event> SecondPerform(Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.SECOND_CONTROL);
        return sm;

    }

    @Override
    public StateMachine<State, Event> internalSuccess(Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.InternalSuccess);
        return sm;
    }

    @Override
    public StateMachine<State, Event> internalFailed(Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.InternalFailed);
        return sm;
    }


    @Override
    public StateMachine<State, Event>    build   (Long orderId) {
        Order order=orderRepositories.getReferenceById(orderId);

        var sm=stateMachineFactory.getStateMachine(Long.toString(order.getId()));
        sm.stop();
        sm.getStateMachineAccessor().doWithAllRegions(sma->{
            sma.resetStateMachine(new DefaultStateMachineContext<>(order.getState(),null,null,null));
        });
        sm.start();
        return sm;

    }

    @Override
    public void senEvent(Long orderId, StateMachine<State, Event> sm, Event event) {
        Message msg= MessageBuilder.withPayload(event)
                .setHeader("ORDER_ID_HEADER",orderId)
                .build();
        sm.sendEvent(msg);
    }


}
