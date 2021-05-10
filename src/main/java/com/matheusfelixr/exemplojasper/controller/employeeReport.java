package com.matheusfelixr.exemplojasper.controller;

import com.matheusfelixr.exemplojasper.domain.enums.ReportType;
import com.matheusfelixr.exemplojasper.domain.model.Employee;
import com.matheusfelixr.exemplojasper.repository.EmployeeRepository;
import com.matheusfelixr.exemplojasper.service.JasperReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class employeeReport {

    @Autowired
    private JasperReportService jasperReportService;

    @Autowired
    private EmployeeRepository repository;

    @GetMapping(value = "/download")
    public ResponseEntity<byte[]> downloadFile() {
        try {
            List<Employee> employees = repository.findAll();
            ByteArrayResource resource = new ByteArrayResource(jasperReportService.generateReport(employees, "employees.jrxml", ReportType.CSV));

            return ResponseEntity.ok()
                    .header("Content-Disposition", "inline; filename=" + "employee.csv")
                    .contentType(MediaType.APPLICATION_CBOR)
                    .body(resource.getByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
