package com.example.demo.controller;

import com.example.demo.domain.Company;
import com.example.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getAll(){
        return companyService.getAll();
    }

    @GetMapping("/{id}")
    public Company getById(@PathVariable Long id){
        return companyService.getById(id);
    }

    @PostMapping("/add")
    public Company addCompany(@RequestBody Company company){
        return companyService.add(company);
    }

    @PostMapping("/update/{id}")
    public Company update(@RequestBody Company company,@PathVariable Long id){
        return companyService.update(id,company);
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return companyService.delete(id);
    }


}
