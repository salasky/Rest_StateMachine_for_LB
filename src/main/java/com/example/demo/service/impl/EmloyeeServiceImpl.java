package com.example.demo.service.impl;

import com.example.demo.domain.Employee;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;
import com.example.demo.repositories.EmployeeRepositories;
import com.example.demo.repositories.SubdivisionRepositories;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.UserService;
import com.example.demo.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmloyeeServiceImpl implements EmployeeService {

    Logger logger = LoggerFactory.getLogger(EmloyeeServiceImpl.class);

    private EmployeeRepositories employeeRepositories;
    private UserService userService;
    private SubdivisionRepositories subdivisionRepositories;
    private Validator validator;

    @Autowired
    public EmloyeeServiceImpl(EmployeeRepositories employeeRepositories, UserService userService, SubdivisionRepositories subdivisionRepositories, Validator validator) {
        this.employeeRepositories = employeeRepositories;
        this.userService = userService;
        this.subdivisionRepositories = subdivisionRepositories;
        this.validator = validator;
    }

    @Override
    public ResponseEntity add(UserDTO userDTO) {

        if (!validator.isValidName(userDTO.getFirst_name())) {
            logger.error("Неправильный формат имени");
            return ResponseEntity.status((HttpStatus.FORBIDDEN)).body("Неправильный формат имени");
        }
        if (!validator.isValidName(userDTO.getSecond_name())) {
            logger.error("Неправильный формат фамилии");
            return ResponseEntity.status((HttpStatus.FORBIDDEN)).body("Неправильный формат фамилии");
        }
        if (!validator.isValidName(userDTO.getFirst_name())) {
            logger.error("Неправильный формат отчества");
            return ResponseEntity.status((HttpStatus.FORBIDDEN)).body("Неправильный формат отчества");
        }

        if (!validator.isValidJobTittle(userDTO.getJob_title())) {
            logger.error("Неправильный формат должности");
            return ResponseEntity.status((HttpStatus.FORBIDDEN)).body("Неправильный формат должности");
        }


        var foundUser=userService.getByLogin(userDTO.getLogin());
        if(foundUser!=null) {
            logger.error("Добавление пользователя.Пользователь с login "+userDTO.getLogin()+ " уже существует");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Пользователь с login "+userDTO.getLogin()+ " уже существует");
        }
        userService.save(new User(userDTO.getLogin(),userDTO.getPassword()));

        var subdivisoin=subdivisionRepositories.findById(userDTO.getSubdivisionId());
        if(subdivisoin.isPresent()){
            var employee=new Employee(userDTO.getLogin(),userDTO.getFirst_name()
                    ,userDTO.getSecond_name(),userDTO.getLast_name()
                    ,userDTO.getJob_title(),subdivisoin.get());
            employeeRepositories.save(employee);
            logger.info("Добавлен новый работник");
            return  ResponseEntity.status(HttpStatus.OK).body(employee);
        }

        logger.error("Добавление пользователя.Subdivision c id "+userDTO.getSubdivisionId()+ " не существует");
        return ResponseEntity.status(HttpStatus.OK).body("Subdivision c id "+userDTO.getSubdivisionId()+ " не существует");

    }

    @Override
    public Employee getById(Long id) {
        logger.info("Выдача информации о работнике");
        return employeeRepositories.findById(id).get();
    }

    @Override
    public List<Employee> getAll() {
        logger.info("Выдача информации о работниках");
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
                    logger.info("Обнавление информации о работнике с id "+id);
                    return employeeRepositories.save(employee);

                })
                .orElseGet(() -> {
                    newemployee.setId(id);
                    logger.info("Обнавление информации о работнике. Добавлен новый пользователь с id "+id);
                    return employeeRepositories.save(newemployee);
                });
    }

    @Override
    public ResponseEntity delete(Long id) {
        try {
            employeeRepositories.deleteById(id);
            logger.info("Удален работник с id "+id);
            return ResponseEntity.status(HttpStatus.OK).body("Employee с id "+ id+ " успешно удален");
        }catch (Exception e){
            logger.error("Удален работника.Работник с id "+id+ " не найден");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee с id "+ id+ " не найден");
        }
    }
}
