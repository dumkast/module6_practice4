package org.example.domain.usecase

import org.example.domain.model.User
import org.example.domain.repository.AuthRepository

class GetUserProfileUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(username: String): User? {
        return authRepository.getUserProfile(username)
    }
}