package org.example.domain.repository

import org.example.domain.model.AuthUser
import org.example.domain.model.User

interface AuthRepository {
    suspend fun login(username: String, password: String): AuthUser?
    suspend fun findUserIdByUsername(username: String): Int?
    suspend fun getUserProfile(username: String): User?
}