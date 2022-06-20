package com.example.demo.controller;

import com.example.demo.domain.Company;
import com.example.demo.domain.Subdivision;
import com.example.demo.service.SubdivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subdivisions")
public class SubdivisionController {
    private SubdivisionService subdivisionService;

    @Autowired
    public SubdivisionController(SubdivisionService subdivisionService) {
        this.subdivisionService = subdivisionService;
    }

    @GetMapping
    public List<Subdivision> getAll(){
        return subdivisionService.getAll();
    }

    @GetMapping("/{id}")
    public Subdivision getById(@PathVariable Long id){
        return subdivisionService.getById(id);
    }

    @PostMapping("/add")
    public Subdivision addCompany(@RequestBody Subdivision subdivision){
        return subdivisionService.add(subdivision);
    }

    @PostMapping("/update/{id}")
    public Subdivision update(@RequestBody Subdivision subdivision,@PathVariable Long id){
        return subdivisionService.update(id,subdivision);
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return subdivisionService.delete(id);
    }
}
