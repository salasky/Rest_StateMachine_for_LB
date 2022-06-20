package com.example.demo.service.impl;

import com.example.demo.domain.Employee;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;
import com.example.demo.repositories.EmployeeRepositories;
import com.example.demo.repositories.SubdivisionRepositories;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmloyeeServiceImpl implements EmployeeService {


    private EmployeeRepositories employeeRepositories;
    private UserService userService;
    private SubdivisionRepositories subdivisionRepositories;

    @Autowired
    public EmloyeeServiceImpl(EmployeeRepositories employeeRepositories, UserService userService, SubdivisionRepositories subdivisionRepositories) {
        this.employeeRepositories = employeeRepositories;
        this.userService = userService;
        this.subdivisionRepositories = subdivisionRepositories;
    }

    @Override
    public ResponseEntity add(UserDTO userDTO) {

        var foundUser=userService.getByLogin(userDTO.getLogin());
        if(foundUser!=null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Пользователь с login "+userDTO.getLogin()+ " уже существует");
        }
        userService.save(new User(userDTO.getLogin(),userDTO.getPassword()));

        var subdivisoin=subdivisionRepositories.findById(userDTO.getSubdivisionId());
        if(subdivisoin.isPresent()){
            var employee=new Employee(userDTO.getLogin(),userDTO.getFirst_name()
                    ,userDTO.getSecond_name(),userDTO.getLast_name()
                    ,userDTO.getJob_title(),subdivisoin.get());
            employeeRepositories.save(employee);
            return  ResponseEntity.status(HttpStatus.OK).body(employee);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Subdivision c id "+userDTO.getSubdivisionId()+ " не существует");

    }

    @Override
    public Employee getById(Long id) {
        return employeeRepositories.findById(id).get();
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepositories.findAll();
    }

    @Override
    public Employee update(Long id, Employee newemployee) {
        return employeeRepositories.findById(id)
                .map(employee ->{
                    employee.setFirst_name(newemployee.getFirst_name());
                    employee.setSecond_name(newemployee.getSecond_name());
                    employee.setLast_name(newemployee.getLast_name());
                    employee.setJob_title(newemployee.getJob_title());
                    employee.setSubdivision(newemployee.getSubdivision());

                    return employeeRepositories.save(employee);

                })
                .orElseGet(() -> {
                    newemployee.setId(id);
                    return employeeRepositories.save(newemployee);
                });
    }

    @Override
    public ResponseEntity delete(Long id) {
        try {
            employeeRepositories.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Employee с id "+ id+ " успешно удален");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee с id "+ id+ " не найден");
        }
    }
}
