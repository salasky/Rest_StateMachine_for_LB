package com.example.demo.dto;

import com.example.demo.domain.Subdivision;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class UserDTO {

    private String login;


    private String password;


    private String first_name;


    private String second_name;


    private String last_name;


    private String job_title;

    private Long subdivisionId;
}
