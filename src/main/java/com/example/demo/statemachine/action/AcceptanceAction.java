package com.example.demo.statemachine.action;

import com.example.demo.statemachine.event.Event;
import com.example.demo.statemachine.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class AcceptanceAction implements Action<State, Event> {
    Logger logger = LoggerFactory.getLogger(AcceptanceAction.class);
    @Override
    public void execute(StateContext<State, Event> stateContext) {
/*        final String orderId = stateContext.getExtendedState().get("Order_ID", String.class);
        logger.info("Поручение с номером " + orderId + " успешно выполнено");*/
    }

}
