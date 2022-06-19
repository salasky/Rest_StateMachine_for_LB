package com.example.demo.statemachine.event;

public enum Event {
    START ,
    FIRST_CONTROL,
    SUCCESS ,
    FAIL_CONTROL,
    InternalSuccess, InternalFailed, SECOND_CONTROL
}
