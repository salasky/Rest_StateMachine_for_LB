package com.example.demo;

import com.example.demo.config.JwtTokenRepository;
import com.example.demo.domain.*;
import com.example.demo.repositories.*;
import com.example.demo.statemachine.state.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.statemachine.processor.StateMachineHandler;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {



    public static void main(String[] args) {


        var context=SpringApplication.run(DemoApplication.class, args);

        var userrepositories=context.getBean(UserRepositories.class);
        var companyRepositories=context.getBean(CompanyRepositories.class);
        var subdivisoinRepositories=context.getBean(SubdivisionRepositories.class);
        var employeeRepositories=context.getBean(EmployeeRepositories.class);
        var orderRepositories=context.getBean(OrderRepositories.class);

     userrepositories.save(
                new User("admin",
                        "admin"));

        Company company=new Company("UralMash","Miass", "Kutuzova","VVP");
        Company company1=new Company("MMG","Miass", "Pushkina","VVP");

        Subdivision subdivision=new Subdivision("HR","URALMASHHR@ms.com","Ivanov",company);
        Subdivision subdivision1=new Subdivision("IT","URALMASHIT@ms.com","Petrov",company1);
        companyRepositories.save(company);
        companyRepositories.save(company1);
        subdivisoinRepositories.save(subdivision);
        subdivisoinRepositories.save(subdivision1);


       Employee employee=new Employee("Krol","Artem",
                "Pavlov","NoBa0","Manager",subdivision);

        Employee employee1=new Employee("SkyNet","Ivan",
                "Kozikov","Rdr","Programmer",subdivision1);

        employeeRepositories.save(employee);
        employeeRepositories.save(employee1);

        List<Employee> employees=new ArrayList<>();
        employees.add(employee1);
        Order order=new Order("Laptop","2022-04-01","orderList",State.PREPARATION,"Buy asus n551vw",employee,employees);
        orderRepositories.save(order);

    }


}
