package com.example.demo.service.impl;

import com.example.demo.domain.Company;
import com.example.demo.domain.Subdivision;
import com.example.demo.dto.SubdivisionDTO;
import com.example.demo.repositories.CompanyRepositories;
import com.example.demo.repositories.EmployeeRepositories;
import com.example.demo.repositories.SubdivisionRepositories;
import com.example.demo.service.SubdivisionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubdivisionServiceImpl implements SubdivisionService {

    Logger logger = LoggerFactory.getLogger(SubdivisionServiceImpl.class);

    private SubdivisionRepositories subdivisionRepositories;
    private EmployeeRepositories employeeRepositories;
    private CompanyRepositories companyRepositories;
    @Autowired
    public SubdivisionServiceImpl(SubdivisionRepositories subdivisionRepositories, EmployeeRepositories employeeRepositories, CompanyRepositories companyRepositories) {
        this.subdivisionRepositories = subdivisionRepositories;
        this.employeeRepositories = employeeRepositories;
        this.companyRepositories = companyRepositories;
    }

    @Override
    public Subdivision getById(Long id) {
        logger.info("Выдача инфрмации о подразделении с id "+id);
        return subdivisionRepositories.findById(id).get();
    }

    @Override
    public List<Subdivision> getAll() {
        logger.info("Выдача инфрмации о подразделениях");
        return subdivisionRepositories.findAll();
    }

    @Override
    public ResponseEntity add(SubdivisionDTO subdivisionDTO) {

        var companys=companyRepositories.findById(subdivisionDTO.getCompanyId());
        if(companys.isPresent()){
            var company=companys.get();
            Subdivision subdivision=new Subdivision(subdivisionDTO.getName(),subdivisionDTO.getContact(),subdivisionDTO.getSupervisor(),company);
            logger.info("Добавление нового подразделения");
            var sub=subdivisionRepositories.save(subdivision);
            return ResponseEntity.status(HttpStatus.OK).body(sub);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Нет компании с данным id");

    }

    @Override
    public Subdivision update(Long id, Subdivision newsubdivision) {

        return subdivisionRepositories.findById(id)
                .map(subdivision ->{
            subdivision.setName(newsubdivision.getName());
            subdivision.setContact(newsubdivision.getContact());
            subdivision.setCompany(newsubdivision.getCompany());
            subdivision.setSupervisor(newsubdivision.getSupervisor());
            logger.info("Обнавление информации о подразделении с id"+id);

            return subdivisionRepositories.save(subdivision);

                })
                .orElseGet(() -> {
                    newsubdivision.setId(id);
                    logger.info("Обнавление информации о подразделении.Создание нового подразделения с id "+id);
                    return subdivisionRepositories.save(newsubdivision);
                });
    }

    @Override
    public ResponseEntity delete(Long id) {
        try {
            subdivisionRepositories.deleteById(id);
            logger.info("Удаление подразделения с id "+id);
            return ResponseEntity.status(HttpStatus.OK).body("Subdivision с id "+ id+ " успешно удален");
        }catch (Exception e){
            logger.error("Удаление подразделения. Подразделение с id "+id+ " не найдено");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subdivision с id "+ id+ " не найден");
        }

    }
}
