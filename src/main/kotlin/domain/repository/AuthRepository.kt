package org.example.domain.repository

import org.example.domain.model.AuthUser

interface AuthRepository {
    fun login(username: String, password: String): AuthUser?
}