package com.example.demo.service;

import com.example.demo.domain.Employee;
import com.example.demo.domain.Subdivision;
import com.example.demo.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {
    ResponseEntity add(UserDTO userDTO);

    Employee getById(Long id);

    List<Employee> getAll();

    Employee update(Long id, Employee employee);

    ResponseEntity delete(Long id);
}
