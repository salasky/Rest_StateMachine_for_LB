package com.example.demo.service;

import com.example.demo.domain.Company;
import com.example.demo.domain.Subdivision;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Company add(Company company);

    Company getById(Long id);

    List<Company> getAll();

    Company update(Long id, Company company);

    ResponseEntity delete(Long id);



}
