package ru.ushakov.userservice.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.ushakov.userservice.model.User
import ru.ushakov.userservice.model.UserRegistrationDetails
import ru.ushakov.userservice.repo.UserRepository

@Service
class SimpleUserRegistrationService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
) : UserRegistrationService {

    override fun register(userDetails: UserRegistrationDetails): User {
        val encodedPassword = passwordEncoder.encode(userDetails.password)
        val user = User(
            username = userDetails.username,
            password = encodedPassword,
            email = userDetails.email
        )
        return userRepository.save(user)
    }
}