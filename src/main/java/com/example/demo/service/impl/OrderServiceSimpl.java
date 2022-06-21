package com.example.demo.service.impl;

import com.example.demo.domain.Employee;
import com.example.demo.domain.Order;
import com.example.demo.dto.OrderDTO;
import com.example.demo.repositories.EmployeeRepositories;
import com.example.demo.repositories.OrderRepositories;
import com.example.demo.service.OrderServiceS;
import com.example.demo.service.UserService;
import com.example.demo.statemachine.event.Event;
import com.example.demo.statemachine.state.State;
import com.example.demo.validator.DateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceSimpl implements OrderServiceS {
    Logger logger = LoggerFactory.getLogger(OrderServiceSimpl.class);
    public static final String PAYMENT_ID_HEADER = "order_id";

    private UserService userService;
    private StateMachineFactory<State, Event> stateMachineFactory;
    private OrderRepositories orderRepositories;
    private DateValidator dateValidator;
    private EmployeeRepositories employeeRepositories;
    private StateMachinePersister<State,Event,String> persister;

    @Autowired
    public OrderServiceSimpl(UserService userService, StateMachineFactory<State, Event> stateMachineFactory, OrderRepositories orderRepositories
            , DateValidator dateValidator, EmployeeRepositories employeeRepositories, StateMachinePersister<State, Event, String> persister) {
        this.userService = userService;
        this.stateMachineFactory = stateMachineFactory;
        this.orderRepositories = orderRepositories;

        this.dateValidator = dateValidator;
        this.employeeRepositories = employeeRepositories;
        this.persister = persister;
    }

    @Override
    public ResponseEntity newOrder(OrderDTO orderDTO) {

        List<Employee> employeesList=new ArrayList<>();

        if(!employeeRepositories.findById(orderDTO.getAuthEmployeeId()).isPresent()){
            logger.error("Автор поручения с id"+orderDTO.getAuthEmployeeId()+" не существует");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Автор поручения с id "+orderDTO.getAuthEmployeeId()+" не существует");
        }

        if(!employeeRepositories.findById(orderDTO.getExecEmployeeId()).isPresent()){
            logger.error("Исполнитель поручения поручения с id "+orderDTO.getExecEmployeeId()+" не существует");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Исполнитель поручения поручения с id"+orderDTO.getExecEmployeeId()+" не существует");
        }

        if(!dateValidator.isValidDate(orderDTO.getPeriodExecution())){
            logger.error("Неверный формат даты исполнения поручения");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Неверный формат даты исполнения поручения\n" +
                    "Формат даты YYYY-MM-DD");
        }

        var employee=employeeRepositories.findById(orderDTO.getExecEmployeeId()).get();
        employeesList.add(employee);

        var order=new Order(orderDTO.getSubject(),orderDTO.getPeriodExecution(),orderDTO.getSignControl(),orderDTO.getOrderText()
                ,employeeRepositories.findById(orderDTO.getAuthEmployeeId()).get()
                ,employeesList);

        order.setState(State.PREPARATION);

        var saveOrder=orderRepositories.save(order);
       var sm=stateMachineFactory.getStateMachine();
        try {
            persister.persist(sm,String.valueOf(saveOrder.getId()));
        } catch (Exception e) {

            logger.error("Не удалось сохранить состояние StateMachine");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Не удалось сохранить состояние StateMachine");
        }

        logger.info("Создано поручение с id "+saveOrder.getId());

        return ResponseEntity.status(HttpStatus.OK).body(saveOrder);

    }



    @Transactional
    @Override
    public StateMachine<State, Event> start (Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.START);
        return sm;
    }
    @Transactional
    @Override
    public StateMachine<State, Event> control(Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.FIRST_CONTROL);
        return sm;
    }
    @Transactional
    @Override
    public StateMachine<State, Event> accept(Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.SUCCESS);
        return sm;
    }
    @Transactional
    @Override
    public StateMachine<State, Event> revision(Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.FAIL_CONTROL);
        return sm;
    }

    @Transactional
    @Override
    public StateMachine<State, Event> SecondPerform(Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.SECOND_CONTROL);
        return sm;

    }
    @Transactional
    @Override
    public StateMachine<State, Event> internalSuccess(Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.InternalSuccess);
        return sm;
    }
    @Transactional
    @Override
    public StateMachine<State, Event> internalFailed(Long orderId) {
        var sm =build(orderId);
        senEvent(orderId,sm,Event.InternalFailed);
        return sm;
    }








    @Override
    public StateMachine<State, Event>    build   (Long orderId) {
        Order order=orderRepositories.getReferenceById(orderId);

        var sm=stateMachineFactory.getStateMachine();

        try {
            persister.restore(sm,String.valueOf(orderId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        sm.getStateMachineAccessor().doWithAllRegions(sma->{
            sma.resetStateMachine(new DefaultStateMachineContext<>(order.getState(),null,null,null));
        });

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
