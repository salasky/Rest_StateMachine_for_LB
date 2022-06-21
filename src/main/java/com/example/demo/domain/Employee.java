package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //IDENTITY - увеличение по правилам в БД
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "second_name")
    private String second_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "job_title")
    private String job_title;


    @ManyToOne
    @JoinColumn(name="subdivision_id")
    private Subdivision subdivision;


    @OneToMany(mappedBy="AuthEmployee")
    private List<Order> AuthOrders;



    @ManyToMany(mappedBy = "ExecEmploye")
    private List<Order> ExecOrders;



    public Employee(String username, String first_name, String second_name, String last_name, String job_title, Subdivision subdivision) {
        this.username = username;
        this.first_name = first_name;
        this.second_name = second_name;
        this.last_name = last_name;
        this.job_title = job_title;
        this.subdivision = subdivision;
    }
}
