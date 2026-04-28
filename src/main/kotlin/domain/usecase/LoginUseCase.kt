package org.example.domain.usecase

import org.example.domain.model.AuthUser
import org.example.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): AuthUser? {
        require(username.isNotBlank()) { "Username cannot be empty" }
        require(password.isNotBlank()) { "Password cannot be empty" }
        return authRepository.authenticate(username, password)
    }
}