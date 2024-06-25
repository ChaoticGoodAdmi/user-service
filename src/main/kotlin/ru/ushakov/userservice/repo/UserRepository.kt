package ru.ushakov.userservice.repo

import org.springframework.data.mongodb.repository.MongoRepository
import ru.ushakov.userservice.model.User

interface UserRepository : MongoRepository<User, String> {
    fun findByUsername(username: String): User?
}
