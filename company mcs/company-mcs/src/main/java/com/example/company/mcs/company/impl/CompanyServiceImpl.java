package com.example.company.mcs.company.impl;


import com.example.company.mcs.company.Company;
import com.example.company.mcs.company.CompanyRepository;
import com.example.company.mcs.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Long id,Company updtCompany) {
        Optional<Company> optionalCompany=companyRepository.findById(id);
        if(optionalCompany.isPresent()){
            Company company=optionalCompany.get();
            company.setName(updtCompany.getName());
            company.setDescription(updtCompany.getDescription());
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompanyById(Long id) {
        if(companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }


}