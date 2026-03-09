package com.m3verificaciones.appweb.company.repository;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.m3verificaciones.appweb.company.model.Company;

import java.util.List;

@SpringBootApplication
public interface CompanyRepository extends CrudRepository<Company, String> {
    // Search for name company
    @Query("SELECT c FROM Company c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Company> findByName(@Param("name") String name);

    @Query("SELECT c FROM Company c WHERE c.uniqueKey = :uniqueKey")
    List<Company> findCompaniesById(@Param("uniqueKey") String uniqueKey);
}
