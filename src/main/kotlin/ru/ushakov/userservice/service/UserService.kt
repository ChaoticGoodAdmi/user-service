package ru.ushakov.userservice.service

import ru.ushakov.userservice.model.User
import ru.ushakov.userservice.model.UserRegistrationDetails

interface UserRegistrationService {

    fun register(userDetails: UserRegistrationDetails): User
}

