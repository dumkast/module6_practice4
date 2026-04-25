package org.example.domain.usecase

import org.example.domain.model.AuthUser
import org.example.domain.repository.AuthRepository


class LoginUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(username: String, password: String): AuthUser? {
        require(username.isNotBlank()) { "Имя пользователя не может быть пустым" }
        require(password.isNotBlank()) { "Пароль не может быть пустым" }
        return authRepository.login(username, password)
    }
}