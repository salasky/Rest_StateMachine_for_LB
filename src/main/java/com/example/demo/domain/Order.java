package com.example.demo.domain;

import com.example.demo.statemachine.state.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //IDENTITY - увеличение по правилам в БД
    @Column(name = "id")
    private Long id;

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

    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "order_text")
    private String orderText;

}
