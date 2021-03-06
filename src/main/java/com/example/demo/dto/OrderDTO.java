package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderDTO {

    private String subject;

    private String periodExecution;


    private String signControl;

    private String orderText;


    private long authEmployeeId;

    private long execEmployeeId;

    public OrderDTO(String subject, String periodExecution, String signControl, String orderText, Long authEmployeeId, Long execEmployeeId) {
        this.subject = subject;
        this.periodExecution = periodExecution;
        this.signControl = signControl;
        this.orderText = orderText;
        this.authEmployeeId = authEmployeeId;
        this.execEmployeeId = execEmployeeId;
    }
}
