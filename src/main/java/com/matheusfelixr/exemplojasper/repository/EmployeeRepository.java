package com.matheusfelixr.exemplojasper.repository;


import com.matheusfelixr.exemplojasper.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
