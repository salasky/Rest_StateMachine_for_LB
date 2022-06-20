package com.example.demo.service;

import com.example.demo.domain.Company;
import com.example.demo.domain.Subdivision;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubdivisionService {
   Subdivision add(Subdivision subdivision);

    Subdivision getById(Long id);

    List<Subdivision> getAll();

    Subdivision update(Long id, Subdivision subdivision);

    ResponseEntity delete(Long id);
}
