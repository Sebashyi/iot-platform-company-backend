package com.m3verificaciones.appweb.company.exception;

import org.springframework.http.HttpStatus;

public class CompanyNotFoundException extends CompanyException {
    public CompanyNotFoundException(String companyId) {
        super("No se encontró la compañía con ID: " + companyId, HttpStatus.NOT_FOUND);
    }
    
    public CompanyNotFoundException(String companyId, Throwable cause) {
        super("No se encontró la compañía con ID: " + companyId, HttpStatus.NOT_FOUND, cause);
    }
}