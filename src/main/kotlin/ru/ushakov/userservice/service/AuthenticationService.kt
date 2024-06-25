package ru.ushakov.userservice.service

import ru.ushakov.userservice.model.UserLoginRequest

interface AuthenticationService {
    fun authenticate(loginRequest: UserLoginRequest): String
}
