package com.example.demo.service.impl;

import com.example.demo.domain.Company;
import com.example.demo.domain.Subdivision;
import com.example.demo.repositories.CompanyRepositories;
import com.example.demo.repositories.SubdivisionRepositories;
import com.example.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CompanyServiceImpl implements CompanyService {


    private CompanyRepositories companyRepositories;
    private SubdivisionRepositories subdivisionRepositories;

    @Autowired
    public CompanyServiceImpl(CompanyRepositories companyRepositories, SubdivisionRepositories subdivisionRepositories) {
        this.companyRepositories = companyRepositories;
        this.subdivisionRepositories = subdivisionRepositories;
    }

    @Override
    public List<Company> getAll() {
        return companyRepositories.findAll();
    }

    @Override
    public Company add(Company company) {
        return companyRepositories.save(company);
    }

    @Override
    public Company getById(Long id) {

        return companyRepositories.findById(id).get();
    }

    @Override
    public Company update(Long id, Company newCompany) {
        return companyRepositories.findById(id)
                .map(company -> {
                    company.setName_of_company(newCompany.getName_of_company());
                    company.setLegal_address(newCompany.getLegal_address());
                    company.setPhysical_adress(newCompany.getPhysical_adress());
                    company.setSupervisor(newCompany.getSupervisor());

                    return companyRepositories.save(company);
                })
                .orElseGet(() -> {
                    newCompany.setId(id);
                    return companyRepositories.save(newCompany);
                });
    }


    @Override
    public ResponseEntity delete(Long id) {
        try {
            companyRepositories.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Company с id "+ id+ " успешно удален");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company с id "+ id+ " не найден");
        }
    }
}
