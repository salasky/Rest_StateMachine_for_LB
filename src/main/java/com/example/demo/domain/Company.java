package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //IDENTITY - увеличение по правилам в БД
    @Column(name = "id")
    private int id;

    @Column(name = "name_of_company")
    private String name_of_company;

    @Column(name = "physical_adress")
    private String physical_adress;

    @Column(name = "legal_address")
    private String legal_address;

    @Column(name = "supervisor")
    private String supervisor;


}
