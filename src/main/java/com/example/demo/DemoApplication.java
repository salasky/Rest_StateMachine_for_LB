package com.example.demo;

import com.example.demo.config.JwtTokenRepository;
import com.example.demo.domain.User;
import com.example.demo.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.statemachine.processor.StateMachineHandler;

import javax.persistence.Column;

@SpringBootApplication
public class DemoApplication {



    public static void main(String[] args) {


        var context=SpringApplication.run(DemoApplication.class, args);

        var userrepositories=context.getBean(UserRepositories.class);
        userrepositories.save(
                new User(1l,"salasky",
                        "password","Salavat","Migranov"
                        ,"Vagizovich","jun"));
        userrepositories.save(
                new User(2l,"skydoom",
                        "password","Ural","Migranov"
                        ,"Vagizovich","jua"));


    }


}
