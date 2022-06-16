package com.example.demo.config;

import com.example.demo.statemachine.action.AcceptanceAction;
import com.example.demo.statemachine.action.ControlAction;
import com.example.demo.statemachine.action.PerformanceAction;
import com.example.demo.statemachine.action.RevisionAction;
import com.example.demo.statemachine.event.Event;
import com.example.demo.statemachine.listener.OrderStateMachineApplicationListener;
import com.example.demo.statemachine.persist.OrderStateMachinePersister;
import com.example.demo.statemachine.state.State;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<State, Event> {



    @Override
    public void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {

        states
                .withStates()
                .initial(State.PREPARATION)
                .state(State.PERFORMANCE)
                .state(State.CONTROL)
                .state(State.REVISION)
                .end(State.ACCEPTANCE);
    }


    @Override
    public void configure(StateMachineConfigurationConfigurer<State, Event> config) throws Exception {

        config
                .withConfiguration()
                .listener(new OrderStateMachineApplicationListener())
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {

        transitions
                .withExternal()
                .source(State.PREPARATION)
                .target(State.PERFORMANCE)
                .event(Event.START)
                .action(performanceAction())


                .and()
                .withExternal()
                .source(State.PERFORMANCE)
                .target(State.CONTROL)
                .event(Event.FIRST_CONTROL)
                .action(controlAction())


                .and()
                .withExternal()
                .source(State.CONTROL)
                .target(State.ACCEPTANCE)
                .event(Event.SUCCESS)
                .action(acceptanceAction())


                .and()
                .withExternal()
                .source(State.CONTROL)
                .target(State.REVISION)
                .event(Event.FAIL_CONTROL)
                .action(revisionAction())


                .and()
                .withExternal()
                .source(State.REVISION)
                .target(State.PERFORMANCE)
                .event(Event.SECOND_CONTROL)
                .action(performanceAction());

    }

    @Bean
    public Action<State, Event> performanceAction() {
        return new PerformanceAction();
    }
    @Bean
    public Action<State, Event> controlAction() {
        return new ControlAction();
    }

    @Bean
    public Action<State, Event> acceptanceAction() {
        return new AcceptanceAction();
    }
    @Bean
    public Action<State, Event> revisionAction() {
        return new RevisionAction();
    }

    @Bean
    public StateMachinePersister<State, Event, String> persister() {
        return new DefaultStateMachinePersister<>(new OrderStateMachinePersister());
    }


}
