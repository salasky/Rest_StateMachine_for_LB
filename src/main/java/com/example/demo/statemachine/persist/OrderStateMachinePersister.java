package com.example.demo.statemachine.persist;

import com.example.demo.statemachine.event.Event;
import com.example.demo.statemachine.state.State;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import java.util.HashMap;


public class OrderStateMachinePersister implements StateMachinePersist<State, Event,String> {

    private HashMap<String, StateMachineContext<State, Event>> contexts = new HashMap<>();
    @Override
    public void write(StateMachineContext<State, Event> stateMachineContext, String s) throws Exception {
        contexts.put(s,stateMachineContext);
    }

    @Override
    public StateMachineContext<State, Event> read(String s) throws Exception {
        return contexts.get(s);
    }

}
