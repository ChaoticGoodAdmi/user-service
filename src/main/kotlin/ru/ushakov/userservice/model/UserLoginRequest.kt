package ru.ushakov.userservice.model

import jakarta.validation.constraints.NotBlank

class UserLoginRequest(
    @field:NotBlank(message = "Username must not be blank")
    val username: String,
    @field:NotBlank(message = "Password must not be blank")
    val password: String,
)
