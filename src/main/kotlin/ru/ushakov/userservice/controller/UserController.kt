package ru.ushakov.userservice.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.ushakov.userservice.model.User
import ru.ushakov.userservice.model.UserLoginRequest
import ru.ushakov.userservice.model.UserRegistrationDetails
import ru.ushakov.userservice.service.AuthenticationService
import ru.ushakov.userservice.service.UserRegistrationService

@RestController
@RequestMapping("/user-service/v1/users")
@Validated
class UserController(
    val userRegistrationService: UserRegistrationService,
    val authService: AuthenticationService,
) {

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody userRegistrationDetails: UserRegistrationDetails): ResponseEntity<User> {
        val user = userRegistrationService.register(userRegistrationDetails)
        return ResponseEntity.status(201).body(user)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginRequest: UserLoginRequest): ResponseEntity<Map<String, String>> {
        val token = authService.authenticate(loginRequest)
        return ResponseEntity.ok(mapOf("token" to token))
    }
}