package com.example.demo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subdivision")
@Getter
@Setter
@NoArgsConstructor
public class Subdivision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //IDENTITY - увеличение по правилам в БД
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "contact")
    private String contact;

    @Column(name = "supervisor")
    private String supervisor;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

    public Subdivision(Long id, String name, String contact, String supervisor, Company company) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.supervisor = supervisor;
        this.company = company;
    }
}
