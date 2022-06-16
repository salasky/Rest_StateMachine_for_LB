package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders2")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //IDENTITY - увеличение по правилам в БД
    @Column(name = "id")
    private int id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "author")
    private String author;

    @Column(name = "executors")
    private String executors;

    @Column(name = "period_execution")
    private String periodExecution;

    @Column(name = "sign_control")
    private String signControl;

    @Column(name = "sign_performance")
    private String signPerformance;

    @Column(name = "order_text")
    private String orderText;

}
