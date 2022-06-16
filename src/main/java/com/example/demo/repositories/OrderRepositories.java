package com.example.demo.repositories;

import com.example.demo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositories extends JpaRepository<Order,Long> {
}
