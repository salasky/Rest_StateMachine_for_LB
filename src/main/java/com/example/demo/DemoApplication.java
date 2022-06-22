package com.example.demo;

import com.example.demo.config.JwtTokenRepository;
import com.example.demo.domain.Company;
import com.example.demo.domain.Employee;
import com.example.demo.domain.Subdivision;
import com.example.demo.domain.User;
import com.example.demo.repositories.*;
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

/*        userrepositories.save(
                new User("admin",
                        "admin"));
        userrepositories.save(
                new User("salasky",
                        "password"));
        userrepositories.save(
                new User("TenserFlow",
                        "password"));

        Company company=new Company("Name","Adress",
                "Adress","Ruk");

        Subdivision subdivision=new Subdivision("11","22","33",company);

        subdivisoinRepositories.save(subdivision);

        Employee employee=new Employee("Krol","Artem",
                "Pavlov","NoBa0","Engineer",subdivision);

        employeeRepositories.save(employee);*/

    }


}
