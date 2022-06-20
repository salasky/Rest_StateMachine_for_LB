package com.example.demo.service.impl;

import com.example.demo.domain.Subdivision;
import com.example.demo.repositories.EmployeeRepositories;
import com.example.demo.repositories.SubdivisionRepositories;
import com.example.demo.service.SubdivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubdivisionServiceImpl implements SubdivisionService {


    private SubdivisionRepositories subdivisionRepositories;
    private EmployeeRepositories employeeRepositories;

    @Autowired
    public SubdivisionServiceImpl(SubdivisionRepositories subdivisionRepositories, EmployeeRepositories employeeRepositories) {
        this.subdivisionRepositories = subdivisionRepositories;
        this.employeeRepositories = employeeRepositories;
    }


    @Override
    public Subdivision getById(Long id) {
        return subdivisionRepositories.findById(id).get();
    }

    @Override
    public List<Subdivision> getAll() {
        return subdivisionRepositories.findAll();
    }

    @Override
    public Subdivision add(Subdivision subdivision) {
        return subdivisionRepositories.save(subdivision);
    }

    @Override
    public Subdivision update(Long id, Subdivision newsubdivision) {
       return subdivisionRepositories.findById(id)
                .map(subdivision ->{
            subdivision.setName(newsubdivision.getName());
            subdivision.setContact(newsubdivision.getContact());
            subdivision.setCompany(newsubdivision.getCompany());
            subdivision.setSupervisor(newsubdivision.getSupervisor());
            return subdivisionRepositories.save(subdivision);

                })
                .orElseGet(() -> {
                    newsubdivision.setId(id);
                    return subdivisionRepositories.save(newsubdivision);
                });
    }

    @Override
    public ResponseEntity delete(Long id) {

        try {
            subdivisionRepositories.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Subdivision с id "+ id+ " успешно удален");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subdivision с id "+ id+ " не найден");
        }

    }
}
