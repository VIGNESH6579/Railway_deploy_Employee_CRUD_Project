package com.example.Employee.Detail.Controlller;

import com.example.Employee.Detail.Model.Employee;
import com.example.Employee.Detail.Service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
// @CrossOrigin(origins = "http://127.0.0.1:5500")
    // after deployment of front end changing cors link
    @CrossOrigin(origins = {
    "http://127.0.0.1:5500",
    "https://employeecrud-frontend.vercel.app",
    "https://employeecrud-frontend-q3kmzxd1x.vercel.app"
})

public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService s) {
        this.employeeService = s;
    }

    // helper to get current authenticated email (your JwtFilter uses email as principal)
    private String currentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;
        Object principal = auth.getPrincipal();
        return principal == null ? null : String.valueOf(principal);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        String email = currentUserEmail();
        if (email == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        employee.setOwnerEmail(email);
        Employee saved = employeeService.createEmployee(employee);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> listMyEmployees() {
        String email = currentUserEmail();
        if (email == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        List<Employee> list = employeeService.getEmployeesForOwner(email);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable String id) {
        String email = currentUserEmail();
        if (email == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            Employee emp = employeeService.getEmployeeByIdForOwner(id, email);
            return ResponseEntity.ok(emp);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable    String id, @RequestBody Employee body) {
        String email = currentUserEmail();
        if (email == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            Employee updated = employeeService.updateEmployeeForOwner(id, body, email);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        String email = currentUserEmail();
        if (email == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            employeeService.deleteEmployeeForOwner(id, email);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
