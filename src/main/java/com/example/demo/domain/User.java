package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "RUser")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //IDENTITY - увеличение по правилам в БД
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "second_name")
    private String second_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "job_title")
    private String job_title;


    public User(Long id, String login, String password, String first_name, String second_name, String last_name, String job_title) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.first_name = first_name;
        this.second_name = second_name;
        this.last_name = last_name;
        this.job_title = job_title;
    }
}
