package com.m3verificaciones.appweb.company.controller;

import com.m3verificaciones.appweb.company.exception.CompanyNotFoundException;
import com.m3verificaciones.appweb.company.model.Company;
import com.m3verificaciones.appweb.company.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping("/m3verificaciones/api/v1/company")
@Tag(name = "Company", description = "Company management API")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(summary = "Get a company by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company found", content = @Content(schema = @Schema(implementation = Company.class))),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Company> findById(@PathVariable String id) {
        Company company = companyService.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(id));
        return ResponseEntity.ok(company);
    }

    @Operation(summary = "Create a new company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company created successfully", content = @Content(schema = @Schema(implementation = Company.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Company company) {
        try {
            Company savedCompany = companyService.save(company);
            return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create company: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a company by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@Valid @PathVariable String id) {
        try {
            if (!companyService.findById(id).isPresent()) {
                return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
            }
            companyService.deleteById(id);
            return new ResponseEntity<>("Company deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete company: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a company by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company updated successfully", content = @Content(schema = @Schema(implementation = Company.class))),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable String id, @RequestBody Company company) {
        try {
            if (!companyService.findById(id).isPresent()) {
                return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
            }
            company.setUniqueKey(id);
            Company updatedCompany = companyService.updateCompany(company);
            return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update company: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all companies with optional filtering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Companies retrieved successfully", content = @Content(schema = @Schema(implementation = Company.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false) String name,
            @RequestParam(required = false) String uniqueKey,
            @RequestParam(required = false) String sort) {
        try {
            List<Company> companies;
            // Search for role
            if (name != null) {
                companies = companyService.searchCompaniesByName(name);
            } else if (uniqueKey != null) {
                companies = companyService.findCompaniesById(uniqueKey);
            }
            // Get all companies
            else {
                companies = companyService.findAll();
            }
            // More params for search if needed
            if (sort != null) {
                // Implement sorting
            }
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve companies: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}