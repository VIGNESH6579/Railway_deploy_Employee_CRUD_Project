package com.example.Employee.Detail.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users") // MongoDB collection name
public class User {

    @Id // MongoDB's unique identifier (_id)
    private String id; // Use String instead of Long for MongoDB IDs

    @Email
    private String email;

    private String password;
}