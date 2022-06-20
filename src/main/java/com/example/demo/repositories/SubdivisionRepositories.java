package com.example.demo.repositories;

import com.example.demo.domain.Company;
import com.example.demo.domain.Subdivision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubdivisionRepositories extends JpaRepository<Subdivision,Long> {
    Subdivision findByCompany(Company company);
}
