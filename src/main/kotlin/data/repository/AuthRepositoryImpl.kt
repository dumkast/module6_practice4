package org.example.data.repository

import org.example.domain.model.AuthUser
import org.example.domain.repository.AuthRepository
import org.example.security.JwtConfig

class AuthRepositoryImpl : AuthRepository {

    private val users = mapOf(
        "admin" to "admin123",
        "user" to "user123",
        "nobel" to "prize2024"
    )

    override fun login(username: String, password: String): AuthUser? {
        if (users[username] == password) {
            val token = JwtConfig.createToken(username)
            return AuthUser(username = username, token = token)
        }
        return null
    }
}