package com.example.demo;

import com.example.demo.config.JwtTokenRepository;
import com.example.demo.domain.Company;
import com.example.demo.domain.Employee;
import com.example.demo.domain.Subdivision;
import com.example.demo.domain.User;
import com.example.demo.repositories.CompanyRepositories;
import com.example.demo.repositories.SubdivisionRepositories;
import com.example.demo.repositories.UserRepositories;
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

        userrepositories.save(
                new User(1l,"user",
                        "user","USER","USER"
                        ,"USER","USER"));
        userrepositories.save(
                new User(2l,"salasky",
                        "password","Salavat","Migranov"
                        ,"Vagizovich","jun"));
        userrepositories.save(
                new User(3l,"TenserFlow",
                        "password","Ivan","Ivanov"
                        ,"Ivanovich","middle"));

        Company company=new Company(1l,"Name","Adress",
                "Adress","Ruk");
        companyRepositories.save(company);

        Subdivision subdivision=new Subdivision(1l,"11","22","33",company);

        subdivisoinRepositories.save(subdivision);

    }


}
