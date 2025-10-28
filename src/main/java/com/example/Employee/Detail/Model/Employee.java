package com.example.Employee.Detail.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "employees") // Specifies the MongoDB collection name
public class Employee {

    @Id // MongoDB's unique identifier (_id)
    private String id; // Use String instead of Long for MongoDB IDs

    private String name;

    @NonNull
    private Integer age;

    private String dept;

    private long salary;

    @Schema(name = "description", example = "type anything in string")
    private String description;

    // Stores the user's email (e.g., from JWT)
    private String ownerEmail;
}