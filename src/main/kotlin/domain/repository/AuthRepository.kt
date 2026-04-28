package org.example.domain.repository

import org.example.domain.model.AuthUser
import org.example.domain.model.User

interface AuthRepository {
    suspend fun authenticate(username: String, password: String): AuthUser?
    suspend fun getUserByUsername(username: String): User?
}