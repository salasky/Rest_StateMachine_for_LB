package com.example.demo.repositories;

import com.example.demo.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepositories extends JpaRepository<Employee,Long> {
}
