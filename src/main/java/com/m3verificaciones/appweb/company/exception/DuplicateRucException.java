package com.m3verificaciones.appweb.company.exception;

import org.springframework.http.HttpStatus;

public class DuplicateRucException extends CompanyException {
    public DuplicateRucException(String ruc) {
        super("El RUC " + ruc + " ya existe en la base de datos", HttpStatus.CONFLICT);
    }
    
    public DuplicateRucException(String ruc, Throwable cause) {
        super("El RUC " + ruc + " ya existe en la base de datos", HttpStatus.CONFLICT, cause);
    }
}