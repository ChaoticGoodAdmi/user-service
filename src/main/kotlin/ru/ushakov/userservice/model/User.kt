package ru.ushakov.userservice.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
class User(
    @Id
    val id: String? = null,
    @NotEmpty(message = "Username is required")
    val username: String,
    @NotEmpty(message = "Password is required")
    val password: String,
    @Email(message = "Email should be valid")
    val email: String,
    val roles: List<String> = listOf("ROLE_USER"),
)