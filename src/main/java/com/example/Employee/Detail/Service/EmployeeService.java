package com.example.Employee.Detail.Service;

import com.example.Employee.Detail.Model.Employee;
import com.example.Employee.Detail.Repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository repo) {
        this.employeeRepository = repo;
    }

    // create: ownerEmail should already be set by controller
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // list for a particular owner
    public List<Employee> getEmployeesForOwner(String ownerEmail) {
        return employeeRepository.findByOwnerEmail(ownerEmail);
    }

    // get single employee only if owner matches
    public Employee getEmployeeByIdForOwner(String id, String ownerEmail) {
        return employeeRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    // update only if owner matches
    public Employee updateEmployeeForOwner(String id, Employee updated, String ownerEmail) {
        Employee existing = employeeRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (updated.getName() != null) existing.setName(updated.getName());
        if (updated.getAge() != null) existing.setAge(updated.getAge());
        if (updated.getDept() != null) existing.setDept(updated.getDept());
        existing.setSalary(updated.getSalary());
        if (updated.getDescription() != null) existing.setDescription(updated.getDescription());
        // ownerEmail remains unchanged

        return employeeRepository.save(existing);
    }

    // delete only if owner matches
    public void deleteEmployeeForOwner(String id, String ownerEmail) {
        Employee existing = employeeRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeRepository.delete(existing);
    }
}
