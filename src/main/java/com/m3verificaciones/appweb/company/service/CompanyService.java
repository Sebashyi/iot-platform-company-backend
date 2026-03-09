package com.m3verificaciones.appweb.company.service;

import com.m3verificaciones.appweb.company.exception.CompanyNotFoundException;
import com.m3verificaciones.appweb.company.exception.DuplicateRucException;
import com.m3verificaciones.appweb.company.model.Company;
import com.m3verificaciones.appweb.company.repository.CompanyRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return (List<Company>) companyRepository.findAll();
    }

    public Optional<Company> findById(String id) {
        return companyRepository.findById(id);
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public void deleteById(String id) {
        companyRepository.deleteById(id);
    }

    public List<Company> searchCompaniesByName(String name) {
        return companyRepository.findByName(name);
    }

    public Company updateCompany(Company updatedCompany) {
        Optional<Company> existingCompanyOpt = companyRepository.findById(updatedCompany.getUniqueKey());
        
        if (existingCompanyOpt.isEmpty()) {
            throw new CompanyNotFoundException(updatedCompany.getUniqueKey());
        }
        
        Company existingCompany = existingCompanyOpt.get();
        if (existingCompany.getRuc().equals(updatedCompany.getRuc()) && 
            !existingCompany.getUniqueKey().equals(updatedCompany.getUniqueKey())) {
            throw new DuplicateRucException(updatedCompany.getRuc());
        }
        
        return companyRepository.save(updatedCompany);
    }

    public List<Company> findCompaniesById(String uniqueKey) {
        return companyRepository.findCompaniesById(uniqueKey); 
    }
}
