package com.example.demo.repositories;

import com.example.demo.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepositories extends JpaRepository<Company,Long> {
}
