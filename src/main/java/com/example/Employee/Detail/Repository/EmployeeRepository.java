package com.example.Employee.Detail.Repository;

import com.example.Employee.Detail.Model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    // find all employees owned by the given user email
    List<Employee> findByOwnerEmail(String ownerEmail);

    // find by id only if owned by given user email
    Optional<Employee> findByIdAndOwnerEmail(String id, String ownerEmail);
}
