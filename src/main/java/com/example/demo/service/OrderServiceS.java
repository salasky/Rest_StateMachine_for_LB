package com.example.demo.service;

public interface OrderServiceS {
    boolean execution(String userId, String orderId);
    boolean control(String orderId);
    boolean accept(String orderId);
    boolean revision(String orderId);
    boolean secondControl(String orderId);
}
